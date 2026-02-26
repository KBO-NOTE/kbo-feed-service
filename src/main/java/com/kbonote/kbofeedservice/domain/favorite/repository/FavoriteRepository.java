package com.kbonote.kbofeedservice.domain.favorite.repository;

import com.kbonote.kbofeedservice.domain.favorite.entity.UserFavoritePlayer;
import com.kbonote.kbofeedservice.domain.favorite.entity.UserFavoritePlayerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<UserFavoritePlayer, UserFavoritePlayerId> {
    List<UserFavoritePlayer> findAllByIdUserId(Long userId);
}