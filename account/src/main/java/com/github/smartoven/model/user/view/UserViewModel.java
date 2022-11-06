package com.github.smartoven.model.user.view;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserViewModel {
    private final Long id;
    private final Long telegramId;
    private final LocalDateTime subscriptionPaidUntil;
}
