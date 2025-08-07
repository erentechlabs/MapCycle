package com.mapcycle.mapcycle.service.leaderboard;

import com.mapcycle.mapcycle.domain.entities.Ride;
import com.mapcycle.mapcycle.domain.entities.User;
import com.mapcycle.mapcycle.repository.RideRepository;
import com.mapcycle.mapcycle.repository.UserChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    @Autowired
    RideRepository rideRepository;

    @Autowired
    UserChallengeRepository userChallengeRepository;


    public List<Map<String, Object>> getDistanceLeaderboard(int days) {
        LocalDate startDate = LocalDate.now().minusDays(days);
        List<Ride> rides = rideRepository.findAll();

        return rides.stream()
                .filter(ride -> !ride.getStartedAt().isBefore(startDate))
                .collect(Collectors.groupingBy(Ride::getUser,
                        Collectors.reducing(BigDecimal.ZERO, Ride::getDistance, BigDecimal::add)))
                .entrySet().stream()
                .sorted(Map.Entry.<User, BigDecimal>comparingByValue().reversed())
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("userId", e.getKey().getId());
                    map.put("username", e.getKey().getUsername());
                    map.put("totalDistance", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }


    public List<Map<String, Object>> getSpeedLeaderboard(int days) {
        LocalDate startDate = LocalDate.now().minusDays(days);
        List<Object[]> data = rideRepository.findLeaderboardByAverageSpeed(startDate);

        return data.stream()
                .map(row -> Map.of(
                        "userId", ((User) row[0]).getId(),
                        "username", ((User) row[0]).getUsername(),
                        "averageSpeed", row[1]))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getChallengesLeaderboard(int days) {
        LocalDate startDate = LocalDate.now().minusDays(days);
        List<Object[]> data = userChallengeRepository.findLeaderboardByCompletedChallenges(startDate);

        return data.stream()
                .map(row -> Map.of(
                        "userId", ((User) row[0]).getId(),
                        "username", ((User) row[0]).getUsername(),
                        "completedChallenges", row[1]))
                .collect(Collectors.toList());
    }
}
