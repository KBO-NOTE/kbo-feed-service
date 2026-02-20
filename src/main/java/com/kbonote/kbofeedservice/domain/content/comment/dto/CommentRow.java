package com.kbonote.kbofeedservice.domain.content.comment.dto;

import java.time.LocalDateTime;

public record CommentRow(Long id, Long userId, String nickname, String content, LocalDateTime createdAt) {
}