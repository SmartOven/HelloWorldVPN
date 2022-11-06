package com.github.smartoven.model.user.services;

import java.util.Optional;

import com.github.smartoven.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByTelegramId(Long telegramId);

    boolean existsByTelegramId(Long telegramId);
}
