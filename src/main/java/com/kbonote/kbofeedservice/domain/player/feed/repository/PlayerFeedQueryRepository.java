package com.kbonote.kbofeedservice.domain.player.feed.repository;

import com.kbonote.kbofeedservice.domain.player.feed.dto.PlayerFeedCursor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerFeedQueryRepository {

    private final EntityManager entityManager;

    public List<PlayerFeedRow> findPersonalizedFeeds(
            Long playerId,
            Long userId,
            PlayerFeedCursor cursor,
            int limit,
            LocalDateTime cutoff
    ) {
        String scoreExpr = """
                COALESCE(SUM(
                    CASE uca.action_type
                        WHEN 'LIKE' THEN 3
                        WHEN 'LIKE_CANCLE' THEN -3
                        WHEN 'COMMENT' THEN 2
                        WHEN 'COMMENT_DELETE' THEN -2
                        WHEN 'CLICK' THEN 3
                        WHEN 'SHARE' THEN 2
                        WHEN 'VIEW' THEN 1
                        ELSE 0
                    END
                ), 0)
                """;

        StringBuilder sql = new StringBuilder("""
                SELECT
                    c.id AS content_id,
                    c.title,
                    c.platform,
                    c.representative_image_url,
                    CASE WHEN EXISTS (
                        SELECT 1
                        FROM content_like cl
                        WHERE cl.content_id = c.id
                          AND cl.user_id = :userId
                          AND cl.status = 'LIKE'
                    ) THEN TRUE ELSE FALSE END AS liked,
                    c.like_count,
                    c.comment_count,
                    c.published_at,
                """).append(scoreExpr).append("""
                     AS score
                FROM content c
                JOIN player_content_map pcm
                  ON pcm.content_id = c.id
                 AND pcm.player_id = :playerId
                LEFT JOIN user_content_action uca
                       ON uca.content_id = c.id
                      AND uca.user_id = :userId
                WHERE c.published_at >= :cutoff
                GROUP BY c.id
                """);

        if (cursor != null) {
            sql.append(" HAVING (")
                    .append(scoreExpr).append(" < :cursorScore")
                    .append(" OR (").append(scoreExpr).append(" = :cursorScore AND c.like_count < :cursorLikeCount)")
                    .append(" OR (").append(scoreExpr).append(" = :cursorScore AND c.like_count = :cursorLikeCount AND c.published_at < :cursorPublishedAt)")
                    .append(" OR (").append(scoreExpr).append(" = :cursorScore AND c.like_count = :cursorLikeCount AND c.published_at = :cursorPublishedAt AND c.id < :cursorContentId)")
                    .append(" )");
        }

        sql.append(" ORDER BY score DESC, c.like_count DESC, c.published_at DESC, c.id DESC");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("playerId", playerId);
        query.setParameter("userId", userId);
        query.setParameter("cutoff", Timestamp.valueOf(cutoff));
        if (cursor != null) {
            query.setParameter("cursorScore", cursor.score());
            query.setParameter("cursorLikeCount", cursor.likeCount());
            query.setParameter("cursorPublishedAt", Timestamp.valueOf(cursor.publishedAt()));
            query.setParameter("cursorContentId", cursor.contentId());
        }
        query.setMaxResults(limit);

        return mapRows(query.getResultList());
    }

    public List<PlayerFeedRow> findDefaultFeeds(
            Long playerId,
            Long userId,
            PlayerFeedCursor cursor,
            int limit,
            LocalDateTime cutoff
    ) {
        StringBuilder sql = new StringBuilder("""
                SELECT
                    c.id AS content_id,
                    c.title,
                    c.platform,
                    c.representative_image_url,
                    CASE WHEN EXISTS (
                        SELECT 1
                        FROM content_like cl
                        WHERE cl.content_id = c.id
                          AND cl.user_id = :userId
                          AND cl.status = 'LIKE'
                    ) THEN TRUE ELSE FALSE END AS liked,
                    c.like_count,
                    c.comment_count,
                    c.published_at,
                    c.like_count AS score
                FROM content c
                JOIN player_content_map pcm
                  ON pcm.content_id = c.id
                 AND pcm.player_id = :playerId
                WHERE c.published_at >= :cutoff
                """);

        if (cursor != null) {
            sql.append("""
                    AND (
                        c.like_count < :cursorScore
                        OR (c.like_count = :cursorScore AND c.published_at < :cursorPublishedAt)
                        OR (c.like_count = :cursorScore AND c.published_at = :cursorPublishedAt AND c.id < :cursorContentId)
                    )
                    """);
        }

        sql.append(" ORDER BY c.like_count DESC, c.published_at DESC, c.id DESC");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("playerId", playerId);
        query.setParameter("userId", userId);
        query.setParameter("cutoff", Timestamp.valueOf(cutoff));
        if (cursor != null) {
            query.setParameter("cursorScore", cursor.score());
            query.setParameter("cursorPublishedAt", Timestamp.valueOf(cursor.publishedAt()));
            query.setParameter("cursorContentId", cursor.contentId());
        }
        query.setMaxResults(limit);

        return mapRows(query.getResultList());
    }

    @SuppressWarnings("unchecked")
    private List<PlayerFeedRow> mapRows(List<?> rows) {
        return ((List<Object[]>) rows).stream()
                .map(row -> new PlayerFeedRow(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        (String) row[2],
                        (String) row[3],
                        (Boolean) row[4],
                        toLong(row[5]),
                        toLong(row[6]),
                        toLocalDateTime(row[7]),
                        toLong(row[8])
                ))
                .toList();
    }

    private Long toLong(Object value) {
        return value == null ? 0L : ((Number) value).longValue();
    }

    private LocalDateTime toLocalDateTime(Object value) {
        if (value instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime();
        }
        return (LocalDateTime) value;
    }
}
