package com.choyeji.kbofeedservice.domain.feed.service.impl;

import com.choyeji.kbofeedservice.domain.feed.dto.FeedHealthResponse;
import com.choyeji.kbofeedservice.domain.feed.repository.FeedRepository;
import com.choyeji.kbofeedservice.domain.feed.service.FeedService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;

    @Override
    public FeedHealthResponse getHealth() {
        return new FeedHealthResponse(
                "kbo-feed-service",
                feedRepository.getHealthStatus(),
                OffsetDateTime.now().toString()
        );
    }
}
