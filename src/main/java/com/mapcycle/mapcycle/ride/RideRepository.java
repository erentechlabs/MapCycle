package com.mapcycle.mapcycle.ride;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    // Find rides by user id
    List<Ride> findByUserId(Long userId);

    /**
     * Return rows of [User, avgSpeed] for rides starting on/after startDate.
     * Note: projection uses r.user (entity) and the aggregated value, so the
     * repository user of this must cast row[0] to User.
     */
    @Query("SELECT r.user, AVG(r.averageSpeed) as avgSpeed " +
            "FROM Ride r " +
            "WHERE r.startedAt >= :startDate " +
            "GROUP BY r.user " +
            "ORDER BY avgSpeed DESC")
    List<Object[]> findLeaderboardByAverageSpeed(LocalDateTime startDate);
}
