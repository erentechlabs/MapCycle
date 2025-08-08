package com.mapcycle.mapcycle.route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findByIsPublicTrue();

    List<Route> findByCreatedById(Long userId);

    List<Route> findTop5ByOrderByRatingDesc();

}
