package com.mapcycle.mapcycle.repositories;

import com.mapcycle.mapcycle.models.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
}
