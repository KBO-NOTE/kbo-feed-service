package com.kbonote.kbofeedservice.domain.content.detail.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record ContentDetailResponse(
        Long id,
        String title,
        @JsonProperty("url") String url,
        @JsonProperty("representative_image_url") String representativeImageUrl,
        @JsonProperty("image_count") Long imageCount,
        @JsonProperty("like_count") Long likeCount,
        @JsonProperty("comment_count") Long commentCount,
        @JsonProperty("published_at") LocalDateTime publishedAt
) {
}
