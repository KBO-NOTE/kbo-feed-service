package com.kbonote.kbofeedservice.domain.favorite.dto;

import java.util.List;

// 나중에는 이런 식으로 넘기는 게 좋습니다.
public record FavoritePlayersResponse(
        List<Long> playerIds
) {}