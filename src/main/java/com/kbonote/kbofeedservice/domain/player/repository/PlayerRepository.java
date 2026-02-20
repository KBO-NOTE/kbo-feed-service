package com.kbonote.kbofeedservice.domain.player.repository;

import com.kbonote.kbofeedservice.domain.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
