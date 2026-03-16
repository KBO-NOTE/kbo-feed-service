package com.kbonote.kbofeedservice.domain.content.entity;

import java.util.Arrays;

public enum PlatformType {
    ARTICLE("article"),
    VIDEO("video"),
    YOUTUBE("youtube");

    private final String dbValue;

    PlatformType(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static PlatformType fromDbValue(String value) {
        return Arrays.stream(values())
                .filter(type -> type.dbValue.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown platform: " + value));
    }
}
