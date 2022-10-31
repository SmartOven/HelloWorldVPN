package com.github.smartoven.account.mapping;

import java.util.List;

import com.github.smartoven.account.entity.Subscription;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserViewModel {
    private final Long id;
    private final Long telegramId;
    private final List<Subscription> subscriptions;
}
