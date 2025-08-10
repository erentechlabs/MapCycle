package com.mapcycle.mapcycle.achievement.challenge;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper challengeMapper;

    @Transactional(readOnly = true)
    public ChallengeDto getDailyChallenge() {
        LocalDate today = LocalDate.now();
        return challengeRepository
                .findFirstByIsActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(today, today)
                .map(challengeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No daily challenge found for today: " + today));
    }

    @Transactional(readOnly = true)
    public List<ChallengeDto> getActiveChallenges() {
        LocalDate today = LocalDate.now();
        return challengeRepository
                .findAllByIsActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(today, today)
                .stream()
                .map(challengeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChallengeDto getChallengeById(Long id) {
        return challengeRepository.findById(id)
                .map(challengeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Challenge not found with id " + id));
    }

    /**
     * Join logic: şimdilik sadece katılımın mümkün olduğunu doğrulayıp ChallengeDto döndürüyoruz.
     * Gerçekte kullanıcı-challenge ilişkisi (participant entity vb.) eklenmeli.
     */
    @Transactional
    public ChallengeDto joinChallenge(Long id) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Challenge not found with id " + id));

        // TODO: Burada kullanıcıyı challenge'a kaydetme işlemi yapılmalı (participant tablosu vs).
        // Örnek amaçlı sadece loglayıp dto döndürüyoruz.
        System.out.println("User joining challenge: " + challenge.getTitle());

        return challengeMapper.toDto(challenge);
    }

    @Transactional(readOnly = true)
    public ChallengeDto getChallengeProgress(Long id) {
        // Bu örnekte progress verisi yok; ileride user-specific progress hesaplanmalı.
        return getChallengeById(id);
    }
}
