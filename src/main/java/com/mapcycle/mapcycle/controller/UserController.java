package com.mapcycle.mapcycle.controller;

import com.mapcycle.mapcycle.domain.entities.User;
import com.mapcycle.mapcycle.service.coreservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(User user) {
        return userService.verify(user);
    }


}
