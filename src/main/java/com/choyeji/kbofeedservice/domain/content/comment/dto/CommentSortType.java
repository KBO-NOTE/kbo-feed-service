package com.choyeji.kbofeedservice.domain.content.comment.dto;

import com.choyeji.kbofeedservice.common.exception.BadRequestException;

public enum CommentSortType {
    LATEST,
    OLDEST;

    public static CommentSortType from(String value) {
        if (value == null || value.isBlank()) {
            return LATEST;
        }
        return switch (value.toLowerCase()) {
            case "latest" -> LATEST;
            case "oldest" -> OLDEST;
            default -> throw new BadRequestException("Invalid sort value. Allowed: latest, oldest");
        };
    }
}
