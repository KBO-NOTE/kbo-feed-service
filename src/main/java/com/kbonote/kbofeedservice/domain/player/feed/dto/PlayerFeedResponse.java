package com.kbonote.kbofeedservice.domain.player.feed.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record PlayerFeedResponse(
        List<PlayerFeedItemResponse> items,
        @JsonProperty("has_next") boolean hasNext,
        @JsonProperty("next_cursor") String nextCursor
) {
}
