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

    @Column(name = "representative_img_url", columnDefinition = "TEXT")
    private String representativeImgUrl;

    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @Column(name = "comment_count", nullable = false)
    private Long commentCount;

    protected Content() {
    }

    public Long getId() {
        return id;
    }

    public PlatformType getPlatform() {
        return platform;
    }

    public String getPress() {
        return press;
    }

    public String getArticleUrlNaver() {
        return articleUrlNaver;
    }

    public String getArticleUrlOrigin() {
        return articleUrlOrigin;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public LocalDateTime getCrawledAt() {
        return crawledAt;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public String getRepresentativeImgUrl() {
        return representativeImgUrl;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }
}
