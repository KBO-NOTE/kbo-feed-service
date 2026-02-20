package com.choyeji.kbofeedservice.domain.content.like.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ContentLikeToggleResponse(
        @JsonProperty("content_id") Long contentId,
        boolean liked,
        @JsonProperty("like_count") Long likeCount
) {
}
