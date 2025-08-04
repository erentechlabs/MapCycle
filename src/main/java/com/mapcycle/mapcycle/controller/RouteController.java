package com.mapcycle.mapcycle.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/routes")
public class RouteController {

    @GetMapping("/nearby")
    public String getNearbyRoutes() {
        return "Get Nearby Routes";
    }

    @GetMapping("/recommendations")
    public String getRouteRecommendations() {
        return "Get Route Recommendations";
    }

    @PostMapping("/calculate")
    public String calculateRoute() {
        return "Calculate Route";
    }

    @GetMapping("/{id}")
    public String getRoute(@PathVariable Long id) {
        return "Get Route";
    }


    @PostMapping("/routes")
    public String createRoute() {
        return "Create Route";
    }


}
