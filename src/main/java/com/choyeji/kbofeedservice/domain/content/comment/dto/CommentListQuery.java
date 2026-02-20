package com.choyeji.kbofeedservice.domain.content.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentListQuery(
        @Schema(description = "Sort order: latest | oldest", example = "latest")
        String sort,
        @Schema(description = "Pagination cursor (Base64)", example = "eyJjcmVhdGVkQXQiOiIyMDI2LTAyLTA0VDEwOjExOjU4IiwiaWQiOjIwMH0=")
        String cursor,
        @Schema(description = "Page size (1~100)", example = "20")
        Integer size
) {
}
