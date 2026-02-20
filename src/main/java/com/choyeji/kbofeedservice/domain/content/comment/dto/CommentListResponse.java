package com.choyeji.kbofeedservice.domain.content.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record CommentListResponse(
        List<CommentItemResponse> comments,
        @JsonProperty("has_next") boolean hasNext,
        @JsonProperty("next_cursor") String nextCursor
) {
}
