package com.github.smartoven.user;

import java.util.List;

import com.github.smartoven.user.entity.UserService;
import com.github.smartoven.user.mapping.UserDto;
import com.github.smartoven.user.mapping.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserViewModel createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/id/{id}")
    public UserViewModel findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/{username}")
    public UserViewModel findUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    @GetMapping
    public List<UserViewModel> findAllUsers() {
        return userService.findAllUsers();
    }
}
