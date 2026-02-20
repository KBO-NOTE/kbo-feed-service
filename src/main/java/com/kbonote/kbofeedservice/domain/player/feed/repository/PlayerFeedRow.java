package com.kbonote.kbofeedservice.domain.player.feed.repository;

import java.time.LocalDateTime;

public record PlayerFeedRow(
        Long contentId,
        String title,
        String platform,
        String representativeImageUrl,
        boolean liked,
        Long likeCount,
        Long commentCount,
        LocalDateTime publishedAt,
        Long score
) {
}
