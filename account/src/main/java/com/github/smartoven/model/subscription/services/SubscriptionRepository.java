package com.github.smartoven.model.subscription.services;

import java.util.Optional;

import com.github.smartoven.model.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByDays(Integer days);

    Optional<Subscription> findByDays(Integer days);
}
