package com.mapcycle.mapcycle.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/challenges")
public class ChallengeController {

    @GetMapping("/daily")
    public String getDailyChallenge() {
        return "Get Daily Challenge";
    }

    @GetMapping("/active")
    public String getActiveChallenges() {
        return "Get Active Challenges";
    }

    @PostMapping("/{id}/join")
    public String joinChallenge(@PathVariable Long id) {
        return "Join Challenge";
    }


    @GetMapping("/{id}/progress")
    public String getChallengeProgress(@PathVariable Long id) {
        return "Get Challenge Progress";
    }
}
