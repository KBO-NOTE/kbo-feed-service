package com.kbonote.kbofeedservice.domain.favorite.service;

import com.kbonote.kbofeedservice.domain.favorite.entity.UserFavoritePlayer;
import com.kbonote.kbofeedservice.domain.favorite.entity.UserFavoritePlayerId;
import com.kbonote.kbofeedservice.domain.favorite.repository.FavoriteRepository;
import com.kbonote.kbofeedservice.domain.player.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final PlayerRepository playerRepository;
    // PlayerRepository, UserRepository 주입 필요 (존재 확인용)

    @Transactional
    public void followPlayer(Long userId, Long playerId) {
        UserFavoritePlayerId id = new UserFavoritePlayerId(playerId, userId);

        if(!playerRepository.existsById(playerId)){
            throw new EntityNotFoundException("존재하지 않는 선수입니다.");
        }

        // 이미 존재하면 예외 처리 (데이터 정합성)
        if (favoriteRepository.existsById(id)) {
            throw new IllegalStateException("이미 팔로우 중인 선수입니다.");
        }

        // 연관관계 없이 ID 조합만으로 바로 저장
        UserFavoritePlayer favorite = new UserFavoritePlayer(id);
        favoriteRepository.save(favorite);
    }

    @Transactional
    public void unfollowPlayer(Long userId, Long playerId) {
        UserFavoritePlayerId id = new UserFavoritePlayerId(playerId, userId);

        if (!favoriteRepository.existsById(id)) {
            throw new IllegalStateException("팔로우 중이 아닌 선수입니다.");
        }

        favoriteRepository.deleteById(id);
    }

    public List<Long> getFavoritePlayerIds(Long userId) {
        return favoriteRepository.findAllByIdUserId(userId)
                .stream()
                .map(favorite -> favorite.getId().getPlayerId())
                .toList();
    }
}