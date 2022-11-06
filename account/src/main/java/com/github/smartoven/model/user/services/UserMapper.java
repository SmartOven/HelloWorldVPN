package com.github.smartoven.model.user.services;

import com.github.smartoven.model.user.User;
import com.github.smartoven.model.user.view.UserDto;
import com.github.smartoven.model.user.view.UserViewModel;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User dtoToEntity(UserDto userDto) {
        return User.builder()
                .telegramId(userDto.getTelegramId())
                .subscriptionPaidUntil(userDto.getSubscriptionPaidUntil())
                .build();
    }

    public UserViewModel entityToViewModel(User user) {
        return new UserViewModel(
                user.getId(),
                user.getTelegramId(),
                user.getSubscriptionPaidUntil()
        );
    }
}
