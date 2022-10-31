package com.github.smartoven.user;

import java.util.Collections;

import com.github.smartoven.user.entity.UserService;
import com.github.smartoven.user.mapping.UserDto;
import com.github.smartoven.user.mapping.UserViewModel;
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

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final UserDto testUserDto = new UserDto(1L);

    @Test
    void createUser() {
        when(userService.createUser(isA(UserDto.class))).thenAnswer(invocation -> {
            UserDto userDto = invocation.getArgument(0);
            return new UserViewModel(1L, userDto.getTelegramId(), Collections.emptyList());
        });

        UserViewModel createdUserViewModel = (UserViewModel) userController.createUser(testUserDto).getBody();
        assertNotNull(createdUserViewModel);
        assertEquals(testUserDto.getTelegramId(), createdUserViewModel.getTelegramId());
    }

    @Test
    void findUserByUsername() {
        when(userService.findUserByTelegramId(isA(Long.class))).thenAnswer(invocation -> {
            Long telegramId = invocation.getArgument(0);
            return new UserViewModel(1L, telegramId, Collections.emptyList());
        });

        UserViewModel foundUserViewModel =
                (UserViewModel) userController.findUserByTelegramId(testUserDto.getTelegramId()).getBody();
        assertNotNull(foundUserViewModel);
        assertEquals(testUserDto.getTelegramId(), foundUserViewModel.getTelegramId());
    }
}