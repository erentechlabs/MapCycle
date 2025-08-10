package com.mapcycle.mapcycle.achievement.challenge;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping("/daily")
    public ResponseEntity<ChallengeDto> getDailyChallenge() {
        ChallengeDto dailyChallenge = challengeService.getDailyChallenge();
        return ResponseEntity.ok(dailyChallenge);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ChallengeDto>> getActiveChallenges() {
        List<ChallengeDto> activeChallenges = challengeService.getActiveChallenges();
        return ResponseEntity.ok(activeChallenges);
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<ChallengeDto> joinChallenge(@PathVariable Long id) {
        ChallengeDto joined = challengeService.joinChallenge(id);
        return ResponseEntity.ok(joined);
    }

    @GetMapping("/{id}/progress")
    public ResponseEntity<ChallengeDto> getChallengeProgress(@PathVariable Long id) {
        ChallengeDto progress = challengeService.getChallengeProgress(id);
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDto> getChallengeById(@PathVariable Long id) {
        ChallengeDto dto = challengeService.getChallengeById(id);
        return ResponseEntity.ok(dto);
    }
}
