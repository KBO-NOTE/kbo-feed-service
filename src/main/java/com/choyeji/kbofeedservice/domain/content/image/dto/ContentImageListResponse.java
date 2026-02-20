package com.choyeji.kbofeedservice.domain.content.image.dto;

import java.util.List;

public record ContentImageListResponse(
        List<ContentImageItemResponse> images
) {
}
