package com.kbonote.kbofeedservice.domain.content.image.dto;

import java.util.List;

public record ContentImageListResponse(
        List<ContentImageItemResponse> images
) {
}