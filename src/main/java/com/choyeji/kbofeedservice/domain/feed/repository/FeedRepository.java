package com.choyeji.kbofeedservice.domain.feed.repository;

import org.springframework.stereotype.Repository;

@Repository
public class FeedRepository {

    public String getHealthStatus() {
        return "UP";
    }
}
