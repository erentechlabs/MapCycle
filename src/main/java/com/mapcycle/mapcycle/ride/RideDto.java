package com.mapcycle.mapcycle.ride;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideDto {

    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    @Size(max = 255, message = "Title can't be longer than 255 characters")
    private String title;

    @NotNull(message = "Distance is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Distance must be greater than 0")
    private BigDecimal distance;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 second")
    private Long duration;

    @NotNull(message = "Average speed is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Average speed must be 0 or greater")
    private BigDecimal averageSpeed;

    @NotNull(message = "Max speed is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Max speed must be 0 or greater")
    private BigDecimal maxSpeed;

    @NotNull(message = "Calories burned is required")
    @Min(value = 0, message = "Calories burned can't be negative")
    private Integer caloriesBurned;

    @NotNull(message = "Elevation gain is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Elevation gain must be 0 or greater")
    private BigDecimal elevationGain;

    @NotNull(message = "Start latitude is required")
    private BigDecimal startLatitude;

    @NotNull(message = "Start longitude is required")
    private BigDecimal startLongitude;

    @NotNull(message = "End latitude is required")
    private BigDecimal endLatitude;

    @NotNull(message = "End longitude is required")
    private BigDecimal endLongitude;

    private String routePolyline;

    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startedAt;

    @NotNull(message = "Finish date is required")
    @PastOrPresent(message = "Finish date must be in the past or present")
    private LocalDate finishedAt;

    private LocalDate createdAt;
}
