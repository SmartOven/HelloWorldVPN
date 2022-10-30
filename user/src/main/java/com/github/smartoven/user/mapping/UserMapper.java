package com.github.smartoven.user.mapping;

import com.github.smartoven.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User dtoToEntity(UserDto userDto) {
        return new User(
                null,
                userDto.getUsername()
        );
    }

    public UserViewModel entityToViewModel(User user) {
        return new UserViewModel(
                user.getId(),
                user.getUsername()
        );
    }
}
