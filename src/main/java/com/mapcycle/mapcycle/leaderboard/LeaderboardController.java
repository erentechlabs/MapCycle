package com.mapcycle.mapcycle.leaderboard;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/distance/{days}")
    public ResponseEntity<List<Map<String, Object>>> getLeaderboardByDistance(@PathVariable int days) {
        return ResponseEntity.ok(leaderboardService.getDistanceLeaderboard(days));
    }

    @GetMapping("/speed/{days}")
    public ResponseEntity<List<Map<String, Object>>> getLeaderboardBySpeed(@PathVariable int days) {
        return ResponseEntity.ok(leaderboardService.getSpeedLeaderboard(days));
    }

    @GetMapping("/challenges/{days}")
    public ResponseEntity<List<Map<String, Object>>> getLeaderboardByChallenges(@PathVariable int days) {
        return ResponseEntity.ok(leaderboardService.getChallengesLeaderboard(days));
    }
}
