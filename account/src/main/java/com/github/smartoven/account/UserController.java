package com.github.smartoven.account;

import java.util.List;
import java.util.Optional;

import com.github.smartoven.account.entity.UserService;
import com.github.smartoven.account.mapping.UserDto;
import com.github.smartoven.account.mapping.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.smartoven.account.exception.ApiResponseCreator.httpBadRequest;
import static com.github.smartoven.account.exception.ApiResponseCreator.httpNotFound;
import static com.github.smartoven.account.exception.ApiResponseCreator.httpOk;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        if (userService.existsByTelegramId(userDto.getTelegramId())) {
            String error = String.format("TelegramId must be unique, found %d", userDto.getTelegramId());
            return httpBadRequest(error, "/api/user");
        }
        return httpOk(userService.createUser(userDto));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        Optional<UserViewModel> userViewModelOptional = userService.findUserById(id);
        if (userViewModelOptional.isPresent()) {
            return httpOk(userViewModelOptional.get());
        }
        return httpNotFound(
                String.format("User with id %s not found", id),
                String.format("/api/user/id/%s", id)
        );
    }

    @GetMapping("/{telegramId}")
    public ResponseEntity<?> findUserByTelegramId(@PathVariable Long telegramId) {
        Optional<UserViewModel> userViewModelOptional = userService.findUserByTelegramId(telegramId);
        if (userViewModelOptional.isPresent()) {
            return httpOk(userViewModelOptional.get());
        }
        return httpNotFound(
                String.format("User with telegramId %s not found", telegramId),
                String.format("/api/user/%s", telegramId)
        );
    }

    @GetMapping
    public List<UserViewModel> findAllUsers() {
        return userService.findAllUsers();
    }

    // TODO Менеджмент подписок
}
