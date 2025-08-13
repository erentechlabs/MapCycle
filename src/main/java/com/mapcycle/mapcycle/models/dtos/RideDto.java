package com.mapcycle.mapcycle.models.dtos;

import com.mapcycle.mapcycle.models.enums.RideStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideDto {

    private Long id;

    @NotNull
    private Long userId;

    private Long routeId;

    @NotNull
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double distanceKm = 0.0;

    private Long elapsedTimeInSeconds;

    @NotNull
    private RideStatus status;

    private String actualRoute;


}
