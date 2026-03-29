package com.kbonote.kbofeedservice.domain.favorite.repository;

import com.kbonote.kbofeedservice.domain.favorite.entity.UserFavoritePlayer;
import com.kbonote.kbofeedservice.domain.favorite.entity.UserFavoritePlayerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<UserFavoritePlayer, UserFavoritePlayerId> {
    List<UserFavoritePlayer> findAllByIdUserId(Long userId);

    @Query("SELECT f.id.playerId FROM UserFavoritePlayer f WHERE f.id.userId = :userId " +
            "ORDER BY (SELECT COUNT(f2) FROM UserFavoritePlayer f2 WHERE f2.id.playerId = f.id.playerId) DESC")
    List<Long> findPlayerIdsByUserIdOrderByFollowCountDesc(@Param("userId") Long userId);
}