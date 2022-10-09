package com.github.smartoven.user.controller;

import com.github.smartoven.user.user.dto.UserDto;
import com.github.smartoven.user.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping
    public List<UserDto> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{username}")
    public UserDto findUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }
}
