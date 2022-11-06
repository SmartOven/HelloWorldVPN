package com.github.smartoven.model.subscription.services;

import java.util.List;
import java.util.Optional;

import com.github.smartoven.model.subscription.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(@Autowired SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    public boolean existsByDays(Integer days) {
        return subscriptionRepository.existsByDays(days);
    }

    public Optional<Subscription> findByDays(Integer days) {
        return subscriptionRepository.findByDays(days);
    }

    public void delete(Subscription subscription) {
        subscriptionRepository.delete(subscription);
    }
}
