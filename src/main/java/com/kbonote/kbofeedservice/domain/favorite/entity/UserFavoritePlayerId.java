package com.kbonote.kbofeedservice.domain.favorite.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserFavoritePlayerId implements Serializable {

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "user_id")
    private Long userId;

    public UserFavoritePlayerId(Long playerId, Long userId) {
        this.playerId = playerId;
        this.userId = userId;
    }
}
