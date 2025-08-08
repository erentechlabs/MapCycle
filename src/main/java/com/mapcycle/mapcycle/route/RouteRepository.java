package com.mapcycle.mapcycle.route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {


    @Query(value = "SELECT * FROM routes r WHERE " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(r.start_latitude)) * " +
            "cos(radians(r.start_longitude) - radians(:lon)) + sin(radians(:lat)) * " +
            "sin(radians(r.start_latitude)))) < :radius", nativeQuery = true)
    List<Route> findRoutesWithinRadius(
            @Param("lat") BigDecimal latitude,
            @Param("lon") BigDecimal longitude,
            @Param("radius") Double radius // km
    );

    List<Route> findTop5ByIsPublicTrueOrderByRatingDesc();

}
