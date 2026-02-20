package com.kbonote.kbofeedservice.domain.content.like.service;

import com.kbonote.kbofeedservice.domain.content.like.dto.ContentLikeToggleResponse;

public interface ContentLikeCommandService {
    ContentLikeToggleResponse toggleLike(Long contentId, Long userId);
}