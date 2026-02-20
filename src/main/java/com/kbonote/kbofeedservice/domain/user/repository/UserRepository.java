package com.kbonote.kbofeedservice.domain.user.repository;

import com.kbonote.kbofeedservice.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}