package com.mapcycle.mapcycle.ride;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @GetMapping
    public ResponseEntity<List<RideDto>> getAllRides() {
        return ResponseEntity.ok(rideService.getAllRides());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideDto> getRideById(@PathVariable Long id) {
        return ResponseEntity.ok(rideService.getRideById(id));
    }

    @PostMapping
    public ResponseEntity<RideDto> createRide(@Valid @RequestBody RideDto rideDto) {
        RideDto created = rideService.createRide(rideDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RideDto> updateRide(@PathVariable Long id,
                                              @Valid @RequestBody RideDto rideDto) {
        return ResponseEntity.ok(rideService.updateRide(id, rideDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Long id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats/{userId}")
    public ResponseEntity<RideService.RideStats> getRideStats(@PathVariable Long userId) {
        return ResponseEntity.ok(rideService.getRideStats(userId));
    }
}
