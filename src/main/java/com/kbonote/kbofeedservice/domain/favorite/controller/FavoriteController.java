package com.kbonote.kbofeedservice.domain.favorite.controller;

import com.kbonote.kbofeedservice.domain.favorite.dto.FavoritePlayersResponse;
import com.kbonote.kbofeedservice.domain.favorite.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Favorite", description = "선수 팔로우(즐겨찾기) API")
@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "선수 팔로우 등록", description = "특정 선수를 나의 관심 선수로 등록합니다.")
    @PostMapping("/players/{playerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void followPlayer(
            @PathVariable("playerId") Long playerId,
            @RequestHeader("X-User-ID") Long userId
    ) {
        favoriteService.followPlayer(userId, playerId);
    }

    @Operation(summary = "선수 팔로우 해제", description = "관심 등록된 선수를 해제합니다.")
    @DeleteMapping("/players/{playerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollowPlayer(
            @PathVariable("playerId") Long playerId,
            @RequestHeader("X-User-ID") Long userId
    ) {
        favoriteService.unfollowPlayer(userId, playerId);
    }


    @Operation(summary = "팔로우 선수 ID 목록 조회", description = "내가 팔로우한 모든 선수의 ID 목록을 반환합니다.")
    @GetMapping
    public FavoritePlayersResponse getFavoritePlayerIds(@RequestHeader("X-User-ID") Long userId) {
        List<Long> playerIds = favoriteService.getFavoritePlayerIds(userId);
        return new FavoritePlayersResponse(playerIds);
    }
}
