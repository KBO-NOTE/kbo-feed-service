package com.kbonote.kbofeedservice.domain.user.profile.dto;

public record UserProfileApiResponse(
        String id,
        String email,
        String name,
        String profileImageUrl
) {
}
