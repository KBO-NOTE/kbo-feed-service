package com.choyeji.kbofeedservice.domain.content.detail.service;

import com.choyeji.kbofeedservice.domain.content.detail.dto.ContentDetailResponse;

public interface ContentDetailQueryService {
    ContentDetailResponse getContentDetail(Long contentId);
}
