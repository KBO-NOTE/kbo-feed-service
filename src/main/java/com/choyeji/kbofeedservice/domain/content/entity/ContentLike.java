package com.choyeji.kbofeedservice.domain.content.entity;

import com.choyeji.kbofeedservice.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "content_like")
public class ContentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContentLikeStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static ContentLike create(Content content, User user) {
        ContentLike contentLike = new ContentLike();
        contentLike.content = content;
        contentLike.user = user;
        contentLike.status = ContentLikeStatus.LIKE;
        contentLike.createdAt = LocalDateTime.now();
        return contentLike;
    }

    public boolean isLiked() {
        return status == ContentLikeStatus.LIKE;
    }

    public void markLiked() {
        this.status = ContentLikeStatus.LIKE;
    }

    public void markCanceled() {
        this.status = ContentLikeStatus.CANCEL;
    }
}
