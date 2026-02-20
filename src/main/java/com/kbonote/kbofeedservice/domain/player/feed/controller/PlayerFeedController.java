package com.kbonote.kbofeedservice.domain.player.feed.controller;

import com.kbonote.kbofeedservice.domain.player.feed.dto.PlayerFeedQuery;
import com.kbonote.kbofeedservice.domain.player.feed.dto.PlayerFeedResponse;
import com.kbonote.kbofeedservice.domain.player.feed.service.PlayerFeedQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Player Feed", description = "Player feed API")
@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerFeedController {

    private final PlayerFeedQueryService playerFeedQueryService;

    @Operation(summary = "Get player related feeds")
    @GetMapping("/{player_id}/feeds")
    public PlayerFeedResponse getPlayerFeeds(
            @PathVariable("player_id") Long playerId,
            @ModelAttribute PlayerFeedQuery query,
            @RequestHeader("X-User-ID") Long userId,
            @RequestHeader("X-User-Role") String userRole
    ) {
        return playerFeedQueryService.getPlayerFeeds(playerId, userId, query);
    }
}
