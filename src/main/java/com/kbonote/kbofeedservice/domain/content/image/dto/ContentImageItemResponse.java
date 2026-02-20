package com.kbonote.kbofeedservice.domain.content.image.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ContentImageItemResponse(
        Long id,
        @JsonProperty("image_url") String imageUrl,
        @JsonProperty("order") Long order
) {
}