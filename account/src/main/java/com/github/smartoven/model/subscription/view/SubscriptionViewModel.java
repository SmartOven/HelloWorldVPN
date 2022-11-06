package com.github.smartoven.model.subscription.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SubscriptionViewModel {
    private final Integer days;
    private final Double price;
}
