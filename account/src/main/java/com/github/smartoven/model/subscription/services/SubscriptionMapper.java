package com.github.smartoven.model.subscription.services;

import com.github.smartoven.model.subscription.Subscription;
import com.github.smartoven.model.subscription.view.SubscriptionDto;
import com.github.smartoven.model.subscription.view.SubscriptionViewModel;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionMapper {
    public Subscription dtoToEntity(SubscriptionDto subscriptionDto) {
        return new Subscription(null, subscriptionDto.getDays(), subscriptionDto.getPrice());
    }

    public SubscriptionViewModel entityToViewModel(Subscription subscription) {
        return new SubscriptionViewModel(subscription.getDays(), subscription.getPrice());
    }
}
