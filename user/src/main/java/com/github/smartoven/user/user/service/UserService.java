package com.github.smartoven.user.user.service;

import com.github.smartoven.user.user.User;
import com.github.smartoven.user.user.dto.UserDto;
import com.github.smartoven.user.user.dto.UserMapper;
import com.github.smartoven.user.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    public UserDto createUser(UserDto userDto) {
        User createdUser = userRepository.save(userMapper.map(userDto));
        return userMapper.map(createdUser);
    }

    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::map)
                .toList();
    }

    public UserDto findUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException(String.format("User with username %s not found", username));
        }
        return userMapper.map(userOptional.get());
    }

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(@Autowired UserRepository userRepository,
                       @Autowired UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
}
