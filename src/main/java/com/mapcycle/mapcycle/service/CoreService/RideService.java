package com.mapcycle.mapcycle.service.coreservice;

import com.mapcycle.mapcycle.domain.entities.Ride;
import com.mapcycle.mapcycle.domain.entities.User;
import com.mapcycle.mapcycle.domain.dto.RideDto;
import com.mapcycle.mapcycle.mapper.RideMapper;
import com.mapcycle.mapcycle.repository.RideRepository;
import com.mapcycle.mapcycle.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RideService {

    @Autowired
    RideRepository rideRepository;

    @Autowired
    UserRepository userRepository;


    @Autowired
    RideMapper rideMapper;


    // Get all rides
    public List<RideDto> getAllRides() {
        return rideRepository.findAll()
                .stream()
                .map(rideMapper::toDto)
                .collect(Collectors.toList());
    }


    // Get a specific ride by ID
    public RideDto getRideById(Long id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ride not found with ID: " + id));
        return rideMapper.toDto(ride);
    }


    // Create a new ride
    @Transactional
    public RideDto createRide(RideDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + dto.getUserId()));

        Ride ride = rideMapper.toEntity(dto);
        ride.setUser(user);

        Ride saved = rideRepository.save(ride);
        return rideMapper.toDto(saved);
    }


    // Update an existing ride
    @Transactional
    public RideDto updateRide(Long id, RideDto dto) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ride not found with ID: " + id));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + dto.getUserId()));

        Ride updated = rideMapper.toEntity(dto);
        updated.setId(id);
        updated.setUser(user);

        Ride saved = rideRepository.save(updated);
        return rideMapper.toDto(saved);
    }


    // Delete a ride
    @Transactional
    public void deleteRide(Long id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ride not found with ID: " + id));
        rideRepository.delete(ride);
    }


    public RideStats getRideStats(Long userId) {
        List<Ride> rides = rideRepository.findByUserId(userId);

        BigDecimal totalDistance = rides.stream()
                .map(Ride::getDistance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalDuration = rides.stream()
                .mapToLong(Ride::getDuration)
                .sum();

        int totalCalories = rides.stream()
                .mapToInt(Ride::getCaloriesBurned)
                .sum();

        return new RideStats(totalDistance, totalDuration, totalCalories);
    }


    public record RideStats(BigDecimal totalDistance, long totalDuration, int totalCalories) {}
}
