package com.github.smartoven.user.entity;

import java.util.List;
import java.util.NoSuchElementException;

import com.github.smartoven.user.mapping.UserDto;
import com.github.smartoven.user.mapping.UserMapper;
import com.github.smartoven.user.mapping.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(
            @Autowired UserRepository userRepository,
            @Autowired UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserViewModel createUser(UserDto userDto) {
        User createdUser = userRepository.save(userMapper.dtoToEntity(userDto));
        return userMapper.entityToViewModel(createdUser);
    }

    public List<UserViewModel> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::entityToViewModel)
                .toList();
    }

    public UserViewModel findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("User with id %s not found", id))
                );
        return userMapper.entityToViewModel(user);
    }

    public UserViewModel findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("User with username %s not found", username))
                );
        return userMapper.entityToViewModel(user);
    }
}
