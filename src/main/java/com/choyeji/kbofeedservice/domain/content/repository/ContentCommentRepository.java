package com.choyeji.kbofeedservice.domain.content.repository;

import com.choyeji.kbofeedservice.domain.content.comment.dto.CommentRow;
import com.choyeji.kbofeedservice.domain.content.entity.ContentComment;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentCommentRepository extends JpaRepository<ContentComment, Long> {

    @Query("""
            select new com.choyeji.kbofeedservice.domain.content.comment.dto.CommentRow(
                cc.id, u.id, u.nickname, cc.comment, cc.createdAt
            )
            from ContentComment cc
            join cc.user u
            where cc.content.id = :contentId
            order by cc.createdAt desc, cc.id desc
            """)
    List<CommentRow> findLatestFirstPage(@Param("contentId") Long contentId, Pageable pageable);

    @Query("""
            select new com.choyeji.kbofeedservice.domain.content.comment.dto.CommentRow(
                cc.id, u.id, u.nickname, cc.comment, cc.createdAt
            )
            from ContentComment cc
            join cc.user u
            where cc.content.id = :contentId
              and (
                  cc.createdAt < :cursorCreatedAt
                  or (cc.createdAt = :cursorCreatedAt and cc.id < :cursorId)
              )
            order by cc.createdAt desc, cc.id desc
            """)
    List<CommentRow> findLatestNextPage(
            @Param("contentId") Long contentId,
            @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("""
            select new com.choyeji.kbofeedservice.domain.content.comment.dto.CommentRow(
                cc.id, u.id, u.nickname, cc.comment, cc.createdAt
            )
            from ContentComment cc
            join cc.user u
            where cc.content.id = :contentId
            order by cc.createdAt asc, cc.id asc
            """)
    List<CommentRow> findOldestFirstPage(@Param("contentId") Long contentId, Pageable pageable);

    @Query("""
            select new com.choyeji.kbofeedservice.domain.content.comment.dto.CommentRow(
                cc.id, u.id, u.nickname, cc.comment, cc.createdAt
            )
            from ContentComment cc
            join cc.user u
            where cc.content.id = :contentId
              and (
                  cc.createdAt > :cursorCreatedAt
                  or (cc.createdAt = :cursorCreatedAt and cc.id > :cursorId)
              )
            order by cc.createdAt asc, cc.id asc
            """)
    List<CommentRow> findOldestNextPage(
            @Param("contentId") Long contentId,
            @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
