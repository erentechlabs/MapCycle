package com.mapcycle.mapcycle.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/routes")
public class RouteController {

    @Autowired
    RouteService routeService;

    @PostMapping("/routes")
    public String createRoute() {
        return "Create Route";
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Route>> getNearbyRoutes() {
        return ResponseEntity.ok(routeService.getNearbyRoutes());
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<Route>> getRouteRecommendations() {
        return ResponseEntity.ok(routeService.getRecommendations());
    }

    @PostMapping("/calculate")
    public ResponseEntity<String> calculateRoute() {
        return ResponseEntity.ok(routeService.calculateRoute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getRoute(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.getRoute(id));
    }

    @PostMapping
    public ResponseEntity<Route> createRoute(@RequestBody Route route,
                                             @RequestParam Long userId) {
        Route createdRoute = routeService.createRoute(route, userId);
        return ResponseEntity.ok(createdRoute);
    }

}
