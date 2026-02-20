package com.kbonote.kbofeedservice.domain.player.feed.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record PlayerFeedItemResponse(
        @JsonProperty("content_id") Long contentId,
        String title,
        String platform,
        @JsonProperty("representative_image_url") String representativeImageUrl,
        boolean liked,
        @JsonProperty("like_count") Long likeCount,
        @JsonProperty("comment_count") Long commentCount,
        @JsonProperty("published_at") LocalDateTime publishedAt,
        Long score
) {
}
