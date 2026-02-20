package com.choyeji.kbofeedservice.domain.content.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PlatformType platform;

    @Column(nullable = false, length = 100)
    private String press;

    @Column(name = "article_url_naver", nullable = false)
    private String articleUrlNaver;

    @Column(name = "article_url_origin")
    private String articleUrlOrigin;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private String author;

    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;

    @Column(name = "crawled_at", nullable = false)
    private LocalDateTime crawledAt;

    @Column(name = "has_video", nullable = false)
    private boolean hasVideo;

    @Column(name = "representative_image_url", columnDefinition = "TEXT")
    private String representativeImgUrl;

    @Column(name = "image_count", nullable = false)
    private Long imageCount;

    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @Column(name = "comment_count", nullable = false)
    private Long commentCount;

    public void increaseLikeCount() {
        long current = likeCount == null ? 0L : likeCount;
        likeCount = current + 1L;
    }

    public void decreaseLikeCount() {
        long current = likeCount == null ? 0L : likeCount;
        likeCount = Math.max(0L, current - 1L);
    }

    public void increaseCommentCount() {
        long current = commentCount == null ? 0L : commentCount;
        commentCount = current + 1L;
    }
}
