package com.kbonote.kbofeedservice.domain.feed.controller;

import com.kbonote.kbofeedservice.domain.feed.dto.FeedHealthResponse;
import com.kbonote.kbofeedservice.domain.feed.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Feed", description = "Feed API")
@RestController
@RequestMapping("/api/v1/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @Operation(summary = "Feed health check")
    @GetMapping("/health")
    public FeedHealthResponse getHealth() {
        return feedService.getHealth();
    }
}