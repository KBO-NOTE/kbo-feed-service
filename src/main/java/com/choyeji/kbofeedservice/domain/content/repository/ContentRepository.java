package com.choyeji.kbofeedservice.domain.content.repository;

import com.choyeji.kbofeedservice.domain.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
