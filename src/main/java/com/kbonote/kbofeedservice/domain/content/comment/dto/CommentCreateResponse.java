package com.kbonote.kbofeedservice.domain.content.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommentCreateResponse(
        @JsonProperty("comment_id") Long commentId
) {
}