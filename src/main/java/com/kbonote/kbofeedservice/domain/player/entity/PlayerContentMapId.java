package com.kbonote.kbofeedservice.domain.player.entity;

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
public class PlayerContentMapId implements Serializable {

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "content_id")
    private Long contentId;

    public PlayerContentMapId(Long playerId, Long contentId) {
        this.playerId = playerId;
        this.contentId = contentId;
    }
}
