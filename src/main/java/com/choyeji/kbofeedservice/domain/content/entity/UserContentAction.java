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

@Entity
@Table(name = "user_content_action")
public class UserContentAction {

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
    @Column(nullable = false, length = 30)
    private PlatformType platform;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false, length = 30)
    private ActionType actionType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected UserContentAction() {
    }

    public Long getId() {
        return id;
    }

    public Content getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public PlatformType getPlatform() {
        return platform;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
