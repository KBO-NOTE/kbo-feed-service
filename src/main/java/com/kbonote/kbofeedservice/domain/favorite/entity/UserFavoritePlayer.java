package com.kbonote.kbofeedservice.domain.favorite.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_favorite_players")
public class UserFavoritePlayer {

    @EmbeddedId
    private UserFavoritePlayerId id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UserFavoritePlayer(UserFavoritePlayerId id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
    }
}