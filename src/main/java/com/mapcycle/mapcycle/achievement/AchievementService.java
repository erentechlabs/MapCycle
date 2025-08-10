package com.mapcycle.mapcycle.achievement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;

    @Transactional(readOnly = true)
    public List<AchievementDto> getAllAchievements() {
        return achievementRepository.findAll()
                .stream()
                .map(achievementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AchievementDto> getUserAchievements() {
        return getAllAchievements(); // TODO: User bazlı filtre eklenecek
    }

    @Transactional(readOnly = true)
    public List<AchievementDto> getAchievementProgress() {
        return getAllAchievements(); // TODO: Progress hesaplama eklenecek
    }
}
