package com.kbonote.kbofeedservice.domain.content.detail.service;

import com.kbonote.kbofeedservice.domain.content.detail.dto.ContentDetailResponse;

public interface ContentDetailQueryService {
    ContentDetailResponse getContentDetail(Long contentId);
}