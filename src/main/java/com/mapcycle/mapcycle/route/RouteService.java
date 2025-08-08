package com.mapcycle.mapcycle.route;

import com.mapcycle.mapcycle.user.User;
import com.mapcycle.mapcycle.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private UserRepository userRepository;

    public Route getRoute(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route not found with id: " + id));
    }

    public List<Route> findNearbyRoutes(
            BigDecimal latitude,
            BigDecimal longitude,
            BigDecimal radiusKm) {
        return routeRepository.findRoutesWithinRadius(latitude, longitude, radiusKm.doubleValue());
    }

    public List<Route> getRecommendations() {
        return routeRepository.findTop5ByIsPublicTrueOrderByRatingDesc();
    }

    public Route createRoute(Route route, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        route.setCreatedBy(user);
        return routeRepository.save(route);
    }


    public Route calculateRouteFromPoints(
            BigDecimal startLat,
            BigDecimal startLon,
            BigDecimal endLat,
            BigDecimal endLon) {

        Route calculatedRoute = new Route();
        calculatedRoute.setName("Calculated Route");
        calculatedRoute.setStartLatitude(startLat);
        calculatedRoute.setStartLongitude(startLon);
        calculatedRoute.setEndLatitude(endLat);
        calculatedRoute.setEndLongitude(endLon);
        calculatedRoute.setIsPublic(false);

        return calculatedRoute;
    }
}