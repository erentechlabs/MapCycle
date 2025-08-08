package com.mapcycle.mapcycle.leaderboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/leaderboard")
public class LeaderboardController {

    @Autowired
    LeaderboardService leaderboardService;

    @GetMapping("/distance/{period}")
    public ResponseEntity<List<Map<String, Object>>> getLeaderboardByDistance(@PathVariable int period) {
        return ResponseEntity.ok(leaderboardService.getDistanceLeaderboard(period));
    }

    @GetMapping("/speed/{period}")
    public ResponseEntity<List<Map<String, Object>>> getLeaderboardBySpeed(@PathVariable int period) {
        return ResponseEntity.ok(leaderboardService.getSpeedLeaderboard(period));
    }

    @GetMapping("/challenges/{period}")
    public ResponseEntity<List<Map<String, Object>>> getLeaderboardByChallenges(@PathVariable int period) {
        return ResponseEntity.ok(leaderboardService.getChallengesLeaderboard(period));
    }

}
