package com.kbonote.kbofeedservice.domain.content.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record CommentItemResponse(
        Long id,
        @JsonProperty("user_id") Long userId,
        String nickname,
        String content,
        @JsonProperty("created_at") LocalDateTime createdAt
) {
}