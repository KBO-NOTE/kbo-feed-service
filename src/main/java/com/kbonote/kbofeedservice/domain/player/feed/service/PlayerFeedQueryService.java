package com.kbonote.kbofeedservice.domain.player.feed.service;

import com.kbonote.kbofeedservice.domain.player.feed.dto.PlayerFeedQuery;
import com.kbonote.kbofeedservice.domain.player.feed.dto.PlayerFeedResponse;

public interface PlayerFeedQueryService {
    PlayerFeedResponse getPlayerFeeds(Long playerId, Long userId, PlayerFeedQuery query);
}
