package com.mapcycle.mapcycle.repository;


import com.mapcycle.mapcycle.domain.entities.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {

    @Query("SELECT uc.user, COUNT(uc.id) as completedCount " +
            "FROM UserChallenge uc " +
            "WHERE uc.isCompleted = true AND uc.completedAt >= :startDate " +
            "GROUP BY uc.user " +
            "ORDER BY completedCount DESC")
    List<Object[]> findLeaderboardByCompletedChallenges(LocalDate startDate);

}
