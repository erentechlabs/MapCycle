package com.mapcycle.mapcycle.achievement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/achievements")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @GetMapping
    public ResponseEntity<List<AchievementDto>> getAchievements() {
        return ResponseEntity.ok(achievementService.getAllAchievements());
    }

    @GetMapping("/user")
    public ResponseEntity<List<AchievementDto>> getUserAchievements() {
        return ResponseEntity.ok(achievementService.getUserAchievements());
    }

    @GetMapping("/progress")
    public ResponseEntity<List<AchievementDto>> getAchievementProgress() {
        return ResponseEntity.ok(achievementService.getAchievementProgress());
    }
}
