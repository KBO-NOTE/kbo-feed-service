package com.kbonote.kbofeedservice.domain.content.image.service;

import com.kbonote.kbofeedservice.domain.content.image.dto.ContentImageListResponse;

public interface ContentImageQueryService {
    ContentImageListResponse getContentImages(Long contentId);
}