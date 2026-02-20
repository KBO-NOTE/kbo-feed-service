package com.kbonote.kbofeedservice.common.exception;

public record ApiErrorResponse(
        String code,
        String message
) {
}
