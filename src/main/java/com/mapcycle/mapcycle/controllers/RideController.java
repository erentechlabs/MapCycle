package com.mapcycle.mapcycle.controllers;

import com.mapcycle.mapcycle.models.dtos.RideDto;
import com.mapcycle.mapcycle.services.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/rides", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideDto> create(@Valid @RequestBody RideDto dto) {
        RideDto created = rideService.create(dto);
        return ResponseEntity.created(URI.create("/api/rides/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideDto> getById(@PathVariable Long id) {
        RideDto dto = rideService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<RideDto>> getAll() {
        List<RideDto> list = rideService.findAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideDto> update(@PathVariable Long id, @Valid @RequestBody RideDto dto) {
        RideDto updated = rideService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rideService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<RideDto> completeRide(
            @PathVariable Long id,
            @RequestParam(name = "finalDistanceKm", required = false) Double finalDistanceKm,
            @RequestParam(name = "endTime", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {
        RideDto completed = rideService.completeRide(id, finalDistanceKm, endTime);
        return ResponseEntity.ok(completed);
    }
}