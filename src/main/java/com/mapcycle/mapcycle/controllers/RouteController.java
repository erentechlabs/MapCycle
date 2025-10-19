package com.mapcycle.mapcycle.controllers;

import com.mapcycle.mapcycle.models.dtos.RouteDto;
import com.mapcycle.mapcycle.services.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/routes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RouteDto> create(@Valid @RequestBody RouteDto dto) {
        RouteDto created = routeService.create(dto);
        return ResponseEntity.created(URI.create("/api/routes/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<RouteDto>> getAll() {
        return ResponseEntity.ok(routeService.findAll());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RouteDto> update(@PathVariable Long id, @Valid @RequestBody RouteDto dto) {
        return ResponseEntity.ok(routeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        routeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}