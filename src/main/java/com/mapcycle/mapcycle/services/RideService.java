package com.mapcycle.mapcycle.services;

import com.mapcycle.mapcycle.models.dtos.RideDto;
import com.mapcycle.mapcycle.models.entities.Ride;
import com.mapcycle.mapcycle.models.entities.Route;
import com.mapcycle.mapcycle.models.entities.User;
import com.mapcycle.mapcycle.models.enums.RideStatus;
import com.mapcycle.mapcycle.models.mappers.RideMapper;
import com.mapcycle.mapcycle.repositories.RideRepository;
import com.mapcycle.mapcycle.repositories.RouteRepository;
import com.mapcycle.mapcycle.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RideService {

    private static final int LEVEL_UP_XP = 500;

    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final RideMapper rideMapper;

    public RideDto create(RideDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + dto.getUserId()));

        Route route = null;
        if (dto.getRouteId() != null) {
            route = routeRepository.findById(dto.getRouteId())
                    .orElseThrow(() -> new EntityNotFoundException("Route not found: " + dto.getRouteId()));
        }

        Ride ride = rideMapper.toEntity(dto);
        ride.setUser(user);
        ride.setRoute(route);

        if (ride.getStatus() == null) {
            ride.setStatus(RideStatus.STARTED);
        }
        if (ride.getDistanceKm() == null) {
            ride.setDistanceKm(0.0);
        }

        Ride saved = rideRepository.save(ride);
        return rideMapper.toDto(saved);
    }

    public RideDto update(Long id, RideDto dto) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ride not found: " + id));

        RideStatus previousStatus = ride.getStatus();

        if (dto.getStartTime() != null) ride.setStartTime(dto.getStartTime());
        if (dto.getEndTime() != null) ride.setEndTime(dto.getEndTime());
        if (dto.getDistanceKm() != null) ride.setDistanceKm(dto.getDistanceKm());
        if (dto.getElapsedTimeInSeconds() != null) ride.setElapsedTimeInSeconds(dto.getElapsedTimeInSeconds());
        if (dto.getStatus() != null) ride.setStatus(dto.getStatus());
        if (dto.getActualRoute() != null) ride.setActualRoute(dto.getActualRoute());

        if (dto.getRouteId() != null) {
            Route route = routeRepository.findById(dto.getRouteId())
                    .orElseThrow(() -> new EntityNotFoundException("Route not found: " + dto.getRouteId()));
            ride.setRoute(route);
        }

        if (previousStatus != RideStatus.COMPLETED && ride.getStatus() == RideStatus.COMPLETED) {
            applyCompletionEffects(ride);
        }

        Ride saved = rideRepository.save(ride);
        return rideMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public RideDto findById(Long id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ride not found: " + id));
        return rideMapper.toDto(ride);
    }

    @Transactional(readOnly = true)
    public List<RideDto> findAll() {
        return rideRepository.findAll().stream()
                .map(rideMapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!rideRepository.existsById(id)) {
            throw new EntityNotFoundException("Ride not found: " + id);
        }
        rideRepository.deleteById(id);
    }

    public RideDto completeRide(Long rideId, Double finalDistanceKm, LocalDateTime endTime) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new EntityNotFoundException("Ride not found: " + rideId));

        ride.setStatus(RideStatus.COMPLETED);
        if (finalDistanceKm != null) ride.setDistanceKm(finalDistanceKm);
        if (endTime != null) ride.setEndTime(endTime);

        if (ride.getElapsedTimeInSeconds() == null && ride.getStartTime() != null && ride.getEndTime() != null) {
            long seconds = Duration.between(ride.getStartTime(), ride.getEndTime()).getSeconds();
            ride.setElapsedTimeInSeconds(Math.max(0, seconds));
        }

        applyCompletionEffects(ride);

        Ride saved = rideRepository.save(ride);
        return rideMapper.toDto(saved);
    }

    private void applyCompletionEffects(Ride ride) {
        User user = ride.getUser();

        // Totals
        if (ride.getDistanceKm() != null) {
            user.setTotalDistance(user.getTotalDistance().add(BigDecimal.valueOf(ride.getDistanceKm())));
        }
        if (ride.getElapsedTimeInSeconds() != null) {
            user.setTotalTime(user.getTotalTime() + ride.getElapsedTimeInSeconds());
        } else if (ride.getStartTime() != null && ride.getEndTime() != null) {
            long seconds = Duration.between(ride.getStartTime(), ride.getEndTime()).getSeconds();
            user.setTotalTime(user.getTotalTime() + Math.max(0, seconds));
        }

        double distance = ride.getDistanceKm() == null ? 0.0 : ride.getDistanceKm();
        int gainedXp = Math.max(10, (int) Math.round(distance * 10.0));
        addExperience(user, gainedXp);

        userRepository.save(user);
    }

    private void addExperience(User user, int xp) {
        int currentXp = user.getExperiencePoints() == null ? 0 : user.getExperiencePoints();
        int newXp = currentXp + xp;
        int level = user.getLevel() == null ? 1 : user.getLevel();

        while (newXp >= LEVEL_UP_XP) {
            newXp -= LEVEL_UP_XP;
            level += 1;
        }

        user.setExperiencePoints(newXp);
        user.setLevel(level);
    }
}