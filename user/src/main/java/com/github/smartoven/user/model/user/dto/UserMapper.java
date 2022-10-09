package com.github.smartoven.user.model.user.dto;

import com.github.smartoven.user.model.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public User map(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .build();
    }

    public UserDto map(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
