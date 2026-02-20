package com.choyeji.kbofeedservice.domain.content.like.service;

import com.choyeji.kbofeedservice.domain.content.like.dto.ContentLikeToggleResponse;

public interface ContentLikeCommandService {
    ContentLikeToggleResponse toggleLike(Long contentId, Long userId);
}
