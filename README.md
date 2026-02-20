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

## 개인화 피드 동작 요약
- 기본 정렬: `like_count DESC, published_at DESC, content_id DESC`
- 개인화 정렬: `score DESC` 우선, 동점 시 기본 정렬 기준으로 tie-break
- 대상 데이터: 최근 1개월 이내 발행 콘텐츠
- 분기 조건: 사용자 행동 점수 기반(`user_content_action`)

## 액션 로그(user_content_action)
다음 행동 시 로그를 저장합니다.
- 콘텐츠 상세 조회(VIEW)
- 좋아요/좋아요 취소(LIKE, LIKE_CANCLE)
- 댓글 작성(COMMENT)

로그 저장 실패 시:
- 메인 기능은 계속 동작
- 에러 로그 출력: `USER_CONTENT_ACTION_LOG_WRITE_FAILED`
- 메트릭 증가: `user_content_action.log.failure`
