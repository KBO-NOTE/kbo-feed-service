package com.choyeji.kbofeedservice.domain.user.repository;

import com.choyeji.kbofeedservice.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
