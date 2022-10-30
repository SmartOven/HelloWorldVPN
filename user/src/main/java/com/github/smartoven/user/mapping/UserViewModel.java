package com.github.smartoven.user.mapping;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserViewModel {
    private final Long id;
    private final String username;
}
