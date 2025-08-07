package com.mapcycle.mapcycle.repository;

import com.mapcycle.mapcycle.domain.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    // Custom query to find rides by user ID
    List<Ride> findByUserId(Long userId);

    @Query("SELECT r.user, AVG(r.averageSpeed) as avgSpeed " +
            "FROM Ride r " +
            "WHERE r.startedAt >= :startDate " +
            "GROUP BY r.user " +
            "ORDER BY avgSpeed DESC")
    List<Object[]> findLeaderboardByAverageSpeed(LocalDate startDate);


}