package com.mapcycle.mapcycle.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @GetMapping("/profile")
    public String profile() {
        return "User Profile";
    }

    @PutMapping("/profile")
    public String updateProfile() {
        return "Update Profile";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id) {
        return "Get User";
    }

    @PostMapping("/upload-avatar")
    public String uploadAvatar() {
        return "Upload Avatar";
    }

}
