# KBO Feed Service

KBO 콘텐츠/피드 조회를 담당하는 Spring Boot 기반 마이크로서비스입니다.

## 기술 스택
- Java 17
- Spring Boot 3.5.6
- Spring Web, Validation, Spring Data JPA
- PostgreSQL
- springdoc-openapi (Swagger UI)
- Actuator (health/info/prometheus)

## 실행 전 준비
1. Java 17 설치
2. PostgreSQL 실행
3. 프로젝트 루트에 `.env` 파일 작성

## 환경 변수
`application.properties`에서 `.env`를 import 하므로 아래 값을 `.env`에 넣어주세요.

```env
SPRING_PROFILES_ACTIVE=local

SERVER_PORT=8080

DB_HOST=localhost
DB_PORT=5432
DB_NAME=kbo_feed_service
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password
```

참고:
- `local` 프로필은 기본값이 일부 포함되어 있고 SQL 로그가 켜져 있습니다.
- `dev` 프로필은 DB 관련 값을 필수로 받습니다.

## 로컬 실행
```bash
./gradlew bootRun
```

Windows:
```powershell
.\gradlew.bat bootRun
```

## 테스트/빌드
```bash
./gradlew test
./gradlew compileJava
```

## API 문서
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## Gateway 헤더 규칙
이 서비스는 API Gateway가 전달한 아래 헤더를 필수로 받습니다.
- `X-User-ID`
- `X-User-Role`

헤더가 없으면 `401 UNAUTHORIZED`를 반환합니다.

## 주요 API
- `GET /api/contents/{content_id}`: 콘텐츠 상세 메타 조회
- `GET /api/contents/{content_id}/comments`: 댓글 목록 조회(커서 기반 무한스크롤)
- `POST /api/contents/{content_id}/comment`: 댓글 작성
- `POST /api/contents/{content_id}/like`: 좋아요 토글
- `GET /api/contents/{content_id}/images`: 콘텐츠 이미지 목록 조회
- `GET /api/players/{player_id}/feeds`: 선수 관련 피드 조회(개인화/비개인화 + 커서 페이징)
- `GET /api/v1/feeds/health`: 서비스 헬스 체크

## 개인화 알고리즘 적용 방식
선수 피드 API(`GET /api/players/{player_id}/feeds`)에서 아래 규칙으로 개인화를 적용합니다.

### 1) 개인화 적용 여부 분기
- 기준: `user_content_action` 기반 사용자 행동 점수
- 계산 로직:
  - 기본 행동(VIEW, LIKE, COMMENT): `+1`
  - 취소/삭제 행동(LIKE_CANCLE, COMMENT_DELETE): `-1`
- 개인화 적용 조건: 누적 점수 `>= 5`
- 점수 `< 5`면 비개인화 정렬 사용

### 2) 후보 콘텐츠 범위
- `player_content_map`으로 선수와 매핑된 콘텐츠만 조회
- 발행일 `published_at` 기준 최근 1개월 이내 콘텐츠만 조회

### 3) 개인화 점수 계산(콘텐츠별)
개인화 모드일 때, 해당 사용자(`X-User-ID`)의 `user_content_action` 로그를 콘텐츠별로 집계해 점수를 계산합니다.

- VIEW: `+1`
- LIKE: `+3`
- LIKE_CANCLE: `-3`
- COMMENT: `+2`
- COMMENT_DELETE: `-2`

### 4) 정렬 규칙
- 개인화 정렬:
  - `score DESC`
  - 동점 시 `like_count DESC`
  - 동점 시 `published_at DESC`
  - 동점 시 `content_id DESC`
- 비개인화 정렬:
  - `like_count DESC, published_at DESC, content_id DESC`

### 5) 커서 페이징
- 무한 스크롤용 커서(Base64) 내부 값:
  - `score`, `like_count`, `published_at`, `content_id`
- 커서는 서버가 생성/해석하는 opaque 값으로 사용

## 액션 로그(user_content_action)
다음 행동 시 로그를 저장합니다.
- 콘텐츠 상세 조회(VIEW)
- 좋아요/좋아요 취소(LIKE, LIKE_CANCLE)
- 댓글 작성(COMMENT)

로그 저장 실패 시:
- 메인 기능은 계속 동작
- 에러 로그 출력: `USER_CONTENT_ACTION_LOG_WRITE_FAILED`
- 메트릭 증가: `user_content_action.log.failure`
