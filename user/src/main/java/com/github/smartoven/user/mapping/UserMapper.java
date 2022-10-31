package com.github.smartoven.user.mapping;

import java.util.Collections;

import com.github.smartoven.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User dtoToEntity(UserDto userDto) {
        return User.builder()
                .telegramId(userDto.getTelegramId())
                .subscriptions(Collections.emptyList())
                .build();
    }

    public UserViewModel entityToViewModel(User user) {
        return new UserViewModel(
                user.getId(),
                user.getTelegramId(),
                user.getSubscriptions()
        );
    }
}
