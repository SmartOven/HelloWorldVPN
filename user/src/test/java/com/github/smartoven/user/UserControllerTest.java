package com.github.smartoven.user;

import com.github.smartoven.user.entity.UserService;
import com.github.smartoven.user.mapping.UserDto;
import com.github.smartoven.user.mapping.UserViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final UserDto testUserDto = new UserDto("test_username");

    @Test
    void createUser() {
        when(userService.createUser(isA(UserDto.class))).thenAnswer(invocation -> {
            UserDto userDto = invocation.getArgument(0);
            return new UserViewModel(1L, userDto.getUsername());
        });

        UserViewModel createdUserViewModel = userController.createUser(testUserDto);
        assertEquals(testUserDto.getUsername(), createdUserViewModel.getUsername());
    }

    @Test
    void findUserByUsername() {
        when(userService.findUserByUsername(isA(String.class))).thenAnswer(invocation -> {
            String username = invocation.getArgument(0);
            return new UserViewModel(1L, username);
        });

        UserViewModel foundUserViewModel = userController.findUserByUsername(testUserDto.getUsername());
        assertEquals(testUserDto.getUsername(), foundUserViewModel.getUsername());
    }
}