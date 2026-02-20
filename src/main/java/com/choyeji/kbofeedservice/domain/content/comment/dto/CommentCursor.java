package com.choyeji.kbofeedservice.domain.content.comment.dto;

import java.time.LocalDateTime;

public record CommentCursor(LocalDateTime createdAt, Long id) {
}
