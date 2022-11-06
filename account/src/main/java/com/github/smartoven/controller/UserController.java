package com.github.smartoven.controller;

import java.util.Optional;
import java.util.stream.Collectors;

import com.github.smartoven.model.user.User;
import com.github.smartoven.model.user.services.UserMapper;
import com.github.smartoven.model.user.services.UserService;
import com.github.smartoven.model.user.view.UserDto;
import com.github.smartoven.model.user.view.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.smartoven.exception.ApiResponseCreator.httpBadRequest;
import static com.github.smartoven.exception.ApiResponseCreator.httpNotFound;
import static com.github.smartoven.exception.ApiResponseCreator.httpOk;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(
            @Autowired UserService userService,
            @Autowired UserMapper userMapper
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        if (userService.existsByTelegramId(userDto.getTelegramId())) {
            String error = String.format("TelegramId must be unique, found %d", userDto.getTelegramId());
            return httpBadRequest(error, "/api/user");
        }
        User user = userMapper.dtoToEntity(userDto);
        return httpOk(userService.save(user));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return httpNotFound(
                    String.format("User with id %s not found", id),
                    String.format("/api/user/id/%s", id)
            );
        }
        UserViewModel userViewModel = userMapper.entityToViewModel(userOptional.get());
        return httpOk(userViewModel);
    }

    @GetMapping("/{telegramId}")
    public ResponseEntity<?> findUserByTelegramId(@PathVariable Long telegramId) {
        Optional<User> userOptional = userService.findUserByTelegramId(telegramId);
        if (userOptional.isEmpty()) {
            return httpNotFound(
                    String.format("User with telegramId %s not found", telegramId),
                    String.format("/api/user/%s", telegramId)
            );
        }
        UserViewModel userViewModel = userMapper.entityToViewModel(userOptional.get());
        return httpOk(userViewModel);
    }

    @GetMapping
    public ResponseEntity<?> findAllUsers() {
        return httpOk(userService.findAll().stream()
                .map(userMapper::entityToViewModel)
                .collect(Collectors.toList()));
    }

    // TODO Менеджмент подписок
}
