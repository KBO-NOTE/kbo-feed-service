package com.kbonote.kbofeedservice.domain.content.repository;

import com.kbonote.kbofeedservice.domain.content.entity.ContentLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentLikeRepository extends JpaRepository<ContentLike, Long> {

    Optional<ContentLike> findByContentIdAndUserId(Long contentId, Long userId);
}