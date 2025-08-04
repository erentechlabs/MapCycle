package com.mapcycle.mapcycle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/achievements")
public class AchievementController {
    @GetMapping
    public String getAchievements() {
        return "Get All Achievements";
    }
    @GetMapping("/user")
    public String getUserAchievements() {
        return "Get User Achievements";
    }
    @GetMapping("progress")
    public String getAchievementProgress() {
        return "Get Achievement Progress";
    }
}
