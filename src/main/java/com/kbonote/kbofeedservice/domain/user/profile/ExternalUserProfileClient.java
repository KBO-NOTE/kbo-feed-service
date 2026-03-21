package com.kbonote.kbofeedservice.domain.user.profile;

import com.kbonote.kbofeedservice.domain.user.profile.dto.UserProfileApiResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class ExternalUserProfileClient implements UserProfileClient {

    private final RestClient restClient;
    private final boolean enabled;

    public ExternalUserProfileClient(
            RestClient.Builder restClientBuilder,
            @Value("${external.user-service.base-url:}") String userServiceBaseUrl
    ) {
        if (userServiceBaseUrl == null || userServiceBaseUrl.isBlank()) {
            this.restClient = null;
            this.enabled = false;
            return;
        }
        this.restClient = restClientBuilder.baseUrl(userServiceBaseUrl).build();
        this.enabled = true;
    }

    @Override
    public Map<Long, UserProfileInfo> getProfiles(Set<Long> userIds) {
        if (!enabled || userIds == null || userIds.isEmpty()) {
            return Map.of();
        }

        String userIdParam = userIds.stream()
                .filter(Objects::nonNull)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        if (userIdParam.isBlank()) {
            return Map.of();
        }

        try {
            UserProfileApiResponse[] responses = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/user")
                            .queryParam("userId", userIdParam)
                            .build())
                    .retrieve()
                    .body(UserProfileApiResponse[].class);

            if (responses == null || responses.length == 0) {
                return Map.of();
            }

            return Arrays.stream(responses)
                    .map(this::toProfileInfo)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(
                            UserProfileInfo::id,
                            Function.identity(),
                            (first, second) -> first
                    ));
        } catch (Exception e) {
            log.warn("Failed to fetch user profiles for userIds={}", userIdParam, e);
            return Map.of();
        }
    }

    private UserProfileInfo toProfileInfo(UserProfileApiResponse response) {
        if (response == null || response.id() == null || response.id().isBlank()) {
            return null;
        }

        try {
            Long id = Long.valueOf(response.id());
            return new UserProfileInfo(id, response.name(), response.profileImageUrl());
        } catch (NumberFormatException e) {
            log.warn("Invalid user id from user profile api. id={}", response.id());
            return null;
        }
    }
}
