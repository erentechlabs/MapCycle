package com.mapcycle.mapcycle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/leaderboard")
public class LeaderboardController {

    @GetMapping("/distance/{period}")
    public String getLeaderboardByDistance(@PathVariable int period) {
        return "Get Leaderboard By Distance";
    }

    @GetMapping("/speed/{period}")
    public String getLeaderboardBySpeed(@PathVariable int period) {
        return "Get Leaderboard By Speed";
    }

    @GetMapping("/challenges/{period}")
    public String getLeaderboardByChallenges(@PathVariable int period) {
        return "Get Leaderboard By Challenges";
    }

}
