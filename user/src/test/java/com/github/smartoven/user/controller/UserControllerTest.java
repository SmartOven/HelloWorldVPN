package com.github.smartoven.user.controller;

import com.github.smartoven.user.model.user.dto.UserDto;
import com.github.smartoven.user.model.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final UserDto testUserDto = UserDto.builder()
            .username("username")
            .build();

    @Test
    void createUser() {
        // Create user
        when(userService.createUser(isA(UserDto.class))).thenAnswer(invocation -> {
            UserDto userDto = invocation.getArgument(0);
            userDto.setId(1L);
            return userDto;
        });

        UserDto createdUserDto = userController.createUser(testUserDto);
        assertEquals(testUserDto.getUsername(), createdUserDto.getUsername());
    }

    @Test
    void findAllUsers() {
        // Find all
        when(userService.findAllUsers()).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> userController.findAllUsers());
        assertNotNull(userController.findAllUsers());
    }

    @Test
    void findUserByUsername() {
        // Find by username
        when(userService.findUserByUsername(isA(String.class))).thenAnswer(invocation -> {
            String username = invocation.getArgument(0);
            return new UserDto(1L, username);
        });

        assertDoesNotThrow(
                () -> userController.findUserByUsername(testUserDto.getUsername())
        );
        UserDto foundUser = userController.findUserByUsername(testUserDto.getUsername());
        assertEquals(testUserDto.getUsername(), foundUser.getUsername());
    }
}