package com.kbonote.kbofeedservice.domain.feed.service;

import com.kbonote.kbofeedservice.domain.feed.dto.FeedHealthResponse;

public interface FeedService {
    FeedHealthResponse getHealth();
}