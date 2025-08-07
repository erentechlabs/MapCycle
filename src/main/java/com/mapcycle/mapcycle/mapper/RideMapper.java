package com.mapcycle.mapcycle.mapper;

import com.mapcycle.mapcycle.domain.entities.Ride;
import com.mapcycle.mapcycle.domain.entities.User;
import com.mapcycle.mapcycle.domain.dto.ride.RideDto;
import org.springframework.stereotype.Component;

@Component
public class RideMapper {

    // Create a RideDto from a Ride entity
    public RideDto toDto(Ride ride) {
        if (ride == null) return null;

        return RideDto.builder()
                .id(ride.getId())
                .userId(ride.getUser() != null ? ride.getUser().getId() : null)
                .title(ride.getTitle())
                .distance(ride.getDistance())
                .duration(ride.getDuration())
                .averageSpeed(ride.getAverageSpeed())
                .maxSpeed(ride.getMaxSpeed())
                .caloriesBurned(ride.getCaloriesBurned())
                .elevationGain(ride.getElevationGain())
                .startLatitude(ride.getStartLatitude())
                .startLongitude(ride.getStartLongitude())
                .endLatitude(ride.getEndLatitude())
                .endLongitude(ride.getEndLongitude())
                .routePolyline(ride.getRoutePolyline())
                .startedAt(ride.getStartedAt())
                .finishedAt(ride.getFinishedAt())
                .createdAt(ride.getCreatedAt())
                .build();
    }

    // Create a Ride entity from a RideDto
    public Ride toEntity(RideDto dto) {
        if (dto == null) return null;
        Ride ride = new Ride();
        ride.setId(dto.getId());
        ride.setTitle(dto.getTitle());
        ride.setDistance(dto.getDistance());
        ride.setDuration(dto.getDuration());
        ride.setAverageSpeed(dto.getAverageSpeed());
        ride.setMaxSpeed(dto.getMaxSpeed());
        ride.setCaloriesBurned(dto.getCaloriesBurned());
        ride.setElevationGain(dto.getElevationGain());
        ride.setStartLatitude(dto.getStartLatitude());
        ride.setStartLongitude(dto.getStartLongitude());
        ride.setEndLatitude(dto.getEndLatitude());
        ride.setEndLongitude(dto.getEndLongitude());
        ride.setRoutePolyline(dto.getRoutePolyline());
        ride.setStartedAt(dto.getStartedAt());
        ride.setFinishedAt(dto.getFinishedAt());

        return ride;
    }

    // Create a Ride entity from a RideDto with a User
    public Ride toEntityWithUser(RideDto dto, User user) {
        Ride ride = toEntity(dto);
        ride.setUser(user);
        return ride;
    }

}
