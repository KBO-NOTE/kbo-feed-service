package com.kbonote.kbofeedservice.domain.user.profile;

public record UserProfileInfo(
        Long id,
        String nickName,
        String profileImageUrl
) {
}
