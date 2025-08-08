package com.mapcycle.mapcycle.ride;

import com.mapcycle.mapcycle.ride.RideService.RideStats;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    @Autowired
    RideService rideService;


    // Get all rides
    @GetMapping
    public ResponseEntity<List<RideDto>> getAllRides() {
        List<RideDto> rides = rideService.getAllRides();
        return ResponseEntity.ok(rides);
    }


    // Get a specific ride by ID
    @GetMapping("/{id}")
    public ResponseEntity<RideDto> getRideById(@PathVariable Long id) {
        RideDto ride = rideService.getRideById(id);
        return ResponseEntity.ok(ride);
    }

    // Create a new ride
    @PostMapping
    public ResponseEntity<RideDto> createRide(@Valid @RequestBody RideDto rideDto) {
        RideDto created = rideService.createRide(rideDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Update an existing ride
    @PutMapping("/{id}")
    public ResponseEntity<RideDto> updateRide(@PathVariable Long id, @Valid @RequestBody RideDto rideDto) {
        RideDto updated = rideService.updateRide(id, rideDto);
        return ResponseEntity.ok(updated);
    }

    // Delete a ride
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Long id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }

    // Get ride stats for a user
    @GetMapping("/stats/{userId}")
    public ResponseEntity<RideStats> getRideStats(@PathVariable Long userId) {
        RideStats stats = rideService.getRideStats(userId);
        return ResponseEntity.ok(stats);
    }
}
