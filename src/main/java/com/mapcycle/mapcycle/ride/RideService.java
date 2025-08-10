package com.mapcycle.mapcycle.ride;

import com.mapcycle.mapcycle.user.User;
import com.mapcycle.mapcycle.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final RideMapper rideMapper;

    // Get all rides
    @Transactional(readOnly = true)
    public List<RideDto> getAllRides() {
        return rideRepository.findAll()
                .stream()
                .map(rideMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get a single ride
    @Transactional(readOnly = true)
    public RideDto getRideById(Long id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ride not found with ID: " + id));
        return rideMapper.toDto(ride);
    }

    // Create
    @Transactional
    public RideDto createRide(RideDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + dto.getUserId()));

        Ride ride = rideMapper.toEntityWithUser(dto, user);
        Ride saved = rideRepository.save(ride);
        return rideMapper.toDto(saved);
    }

    // Update
    @Transactional
    public RideDto updateRide(Long id, RideDto dto) {
        Ride existing = rideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ride not found with ID: " + id));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + dto.getUserId()));

        Ride updated = rideMapper.toEntityWithUser(dto, user);
        updated.setId(id);
        // preserve createdAt from existing record
        updated.setCreatedAt(existing.getCreatedAt());

        Ride saved = rideRepository.save(updated);
        return rideMapper.toDto(saved);
    }

    // Delete
    @Transactional
    public void deleteRide(Long id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ride not found with ID: " + id));
        rideRepository.delete(ride);
    }

    // Stats for a user
    @Transactional(readOnly = true)
    public RideStats getRideStats(Long userId) {
        List<Ride> rides = rideRepository.findByUserId(userId);

        BigDecimal totalDistance = rides.stream()
                .map(r -> r.getDistance() == null ? BigDecimal.ZERO : r.getDistance())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalDuration = rides.stream()
                .mapToLong(r -> r.getDuration() == null ? 0L : r.getDuration())
                .sum();

        int totalCalories = rides.stream()
                .mapToInt(r -> r.getCaloriesBurned() == null ? 0 : r.getCaloriesBurned())
                .sum();

        return new RideStats(totalDistance, totalDuration, totalCalories);
    }

    public record RideStats(BigDecimal totalDistance, long totalDuration, int totalCalories) {
    }
}
