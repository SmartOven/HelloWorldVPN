package com.github.smartoven.account;

import java.time.LocalDateTime;
import java.util.Optional;

import com.github.smartoven.controller.UserController;
import com.github.smartoven.model.user.User;
import com.github.smartoven.model.user.services.UserMapper;
import com.github.smartoven.model.user.services.UserService;
import com.github.smartoven.model.user.view.UserDto;
import com.github.smartoven.model.user.view.UserViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    // FIXME Пофиксить тесты и написать новые, когда код будет готов
    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser() {
        UserDto testUserDto = new UserDto(1L, LocalDateTime.now());

        when(userMapper.dtoToEntity(isA(UserDto.class))).thenAnswer(invocation -> {
            UserDto userDto = invocation.getArgument(0);
            return User.builder()
                    .telegramId(userDto.getTelegramId())
                    .subscriptionPaidUntil(userDto.getSubscriptionPaidUntil())
                    .build();
        });
        when(userService.save(isA(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        UserViewModel createdUserViewModel = (UserViewModel) userController.createUser(testUserDto).getBody();
        assertNotNull(createdUserViewModel);
        assertEquals(testUserDto.getTelegramId(), createdUserViewModel.getTelegramId());
    }

    @Test
    void findUserByUsername() {
        UserDto testUserDto = new UserDto(1L, LocalDateTime.now());

        when(userService.findUserByTelegramId(isA(Long.class))).thenAnswer(invocation -> {
            Long telegramId = invocation.getArgument(0);
            return Optional.of(new User(1L, telegramId, LocalDateTime.now()));
        });

        UserViewModel foundUserViewModel =
                (UserViewModel) userController.findUserByTelegramId(testUserDto.getTelegramId()).getBody();
        assertNotNull(foundUserViewModel);
        assertEquals(testUserDto.getTelegramId(), foundUserViewModel.getTelegramId());
    }
}