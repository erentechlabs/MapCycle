package com.mapcycle.mapcycle.achievement.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    // Tüm aktif ve bugünü kapsayan challenge'ları getir
    List<Challenge> findAllByIsActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate startDate, LocalDate endDate);

    // Bugünü kapsayan ilk aktif challenge'ı getir (günlük challenge için)
    Optional<Challenge> findFirstByIsActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate startDate, LocalDate endDate);
}
