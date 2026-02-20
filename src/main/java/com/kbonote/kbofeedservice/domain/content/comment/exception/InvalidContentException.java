package com.kbonote.kbofeedservice.domain.content.comment.exception;

public class InvalidContentException extends RuntimeException {
    public InvalidContentException(String message) {
        super(message);
    }
}