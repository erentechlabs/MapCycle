package com.mapcycle.mapcycle.repository;

import com.mapcycle.mapcycle.domain.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    // Custom query to find rides by user ID
    List<Ride> findByUserId(Long userId);
}