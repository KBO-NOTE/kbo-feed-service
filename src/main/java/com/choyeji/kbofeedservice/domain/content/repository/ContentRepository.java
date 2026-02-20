package com.choyeji.kbofeedservice.domain.content.repository;

import com.choyeji.kbofeedservice.domain.content.entity.Content;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentRepository extends JpaRepository<Content, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Content c where c.id = :contentId")
    Optional<Content> findByIdForUpdate(@Param("contentId") Long contentId);
}
