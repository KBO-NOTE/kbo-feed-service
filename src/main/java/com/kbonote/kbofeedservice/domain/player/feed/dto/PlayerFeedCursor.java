package com.kbonote.kbofeedservice.domain.player.feed.dto;

import java.time.LocalDateTime;

public record PlayerFeedCursor(Long score, Long likeCount, LocalDateTime publishedAt, Long contentId) {
}
