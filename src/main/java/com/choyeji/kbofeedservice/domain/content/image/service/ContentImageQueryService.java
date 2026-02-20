package com.choyeji.kbofeedservice.domain.content.image.service;

import com.choyeji.kbofeedservice.domain.content.image.dto.ContentImageListResponse;

public interface ContentImageQueryService {
    ContentImageListResponse getContentImages(Long contentId);
}
