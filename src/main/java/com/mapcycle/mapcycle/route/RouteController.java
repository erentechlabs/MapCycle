package com.mapcycle.mapcycle.route;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    private final RouteMapper routeMapper;


    @GetMapping("/{id}")
    public ResponseEntity<RouteDto> getRoute(@PathVariable Long id) {
        Route route = routeService.getRoute(id);
        return ResponseEntity.ok(routeMapper.toDto(route));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<RouteDto>> getNearbyRoutes(
            @RequestParam("lat") BigDecimal latitude,
            @RequestParam("lon") BigDecimal longitude,
            @RequestParam(value = "radius", defaultValue = "5.0") BigDecimal radiusKm) {

        List<RouteDto> routeDtos = routeService
                .findNearbyRoutes(latitude, longitude, radiusKm)
                .stream()
                .map(routeMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(routeDtos);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<RouteDto>> getRouteRecommendations() {
        List<RouteDto> routeDtos = routeService
                .getRecommendations()
                .stream()
                .map(routeMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(routeDtos);
    }

    @PostMapping
    public ResponseEntity<RouteDto> createRoute(@Valid @RequestBody RouteDto routeDto) {
        Route createdRoute = routeService.createRoute(routeMapper.toEntity(routeDto), routeDto.getCreatedById());
        return new ResponseEntity<>(routeMapper.toDto(createdRoute), HttpStatus.CREATED);
    }

    @PostMapping("/calculate")
    public ResponseEntity<RouteDto> calculateRoute(
            @RequestParam("startLat") BigDecimal startLat,
            @RequestParam("startLon") BigDecimal startLon,
            @RequestParam("endLat") BigDecimal endLat,
            @RequestParam("endLon") BigDecimal endLon) {

        Route calculatedRoute = routeService.calculateRouteFromPoints(startLat, startLon, endLat, endLon);
        return ResponseEntity.ok(routeMapper.toDto(calculatedRoute));
    }
}
