package com.mapcycle.mapcycle.route;

import com.mapcycle.mapcycle.user.User;
import com.mapcycle.mapcycle.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    UserRepository userRepository;

    public List<Route> getNearbyRoutes() {
        return routeRepository.findByIsPublicTrue();
    }

    public List<Route> getRecommendations() {
        return routeRepository.findTop5ByOrderByRatingDesc();
    }

    public Route getRoute(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route not found with id: " + id));
    }

    public Route createRoute(Route route, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        route.setCreatedBy(user);
        return routeRepository.save(route);
    }

    public String calculateRoute() {
        return "Route calculation logic is not implemented yet.";
    }
}