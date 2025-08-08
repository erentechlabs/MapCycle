package com.mapcycle.mapcycle.route;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteMapper routeMapper;

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

        List<Route> routes = routeService.findNearbyRoutes(latitude, longitude, radiusKm);
        List<RouteDto> routeDtos = routes.stream().map(routeMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(routeDtos);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<RouteDto>> getRouteRecommendations() {
        List<Route> routes = routeService.getRecommendations();
        List<RouteDto> routeDtos = routes.stream().map(routeMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(routeDtos);
    }

    @PostMapping
    public ResponseEntity<RouteDto> createRoute(@Valid @RequestBody RouteDto routeDto) {
        Route routeToCreate = routeMapper.toEntity(routeDto);
        Route createdRoute = routeService.createRoute(routeToCreate, routeDto.getCreatedById());
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