package com.github.smartoven.account.entity;

import java.util.List;
import java.util.Optional;

import com.github.smartoven.account.mapping.UserDto;
import com.github.smartoven.account.mapping.UserMapper;
import com.github.smartoven.account.mapping.UserViewModel;
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

    public boolean existsByTelegramId(Long telegramId) {
        return userRepository.existsByTelegramId(telegramId);
    }

    public Optional<UserViewModel> findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(userMapper::entityToViewModel);
    }

    public Optional<UserViewModel> findUserByTelegramId(Long telegramId) {
        Optional<User> userOptional = userRepository.findUserByTelegramId(telegramId);
        return userOptional.map(userMapper::entityToViewModel);
    }
}
