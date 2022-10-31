package com.github.smartoven.account.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByTelegramId(Long telegramId);

    boolean existsByTelegramId(Long telegramId);
}
