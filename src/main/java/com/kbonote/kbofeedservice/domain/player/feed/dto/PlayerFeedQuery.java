package com.kbonote.kbofeedservice.domain.player.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PlayerFeedQuery(
        @Schema(description = "Pagination cursor (Base64 opaque)")
        String cursor,
        @Schema(description = "Page size (default: 5, max: 50)", example = "5")
        Integer size
) {
}
