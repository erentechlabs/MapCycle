package com.mapcycle.mapcycle.route;

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
public class RouteDto {

    @NotNull(message = "ID cannot be null")
    @Positive(message = "ID must be a positive number")
    private Long id;

    @NotNull(message = "Creator ID cannot be null")
    @Positive(message = "Creator ID must be a positive number")
    private Long createdById;

    @NotBlank(message = "Creator username cannot be blank")
    private String createdByUsername;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;

    @NotNull(message = "Difficulty level is required")
    @Min(value = 1, message = "Difficulty level must be at least 1")
    @Max(value = 5, message = "Difficulty level must be at most 5")
    private Integer difficultyLevel;

    @Positive(message = "Estimated duration must be a positive number")
    private Integer estimatedDuration;

    @Positive(message = "Distance must be a positive number")
    private BigDecimal distance;

    @NotNull(message = "Start latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude must be greater than or equal to -90")
    @DecimalMax(value = "90.0", message = "Latitude must be less than or equal to 90")
    private BigDecimal startLatitude;

    @NotNull(message = "Start longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be greater than or equal to -180")
    @DecimalMax(value = "180.0", message = "Longitude must be less than or equal to 180")
    private BigDecimal startLongitude;

    @DecimalMin(value = "-90.0", message = "Latitude must be greater than or equal to -90")
    @DecimalMax(value = "90.0", message = "Latitude must be less than or equal to 90")
    private BigDecimal endLatitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be greater than or equal to -180")
    @DecimalMax(value = "180.0", message = "Longitude must be less than or equal to 180")
    private BigDecimal endLongitude;

    private String waypoints;

    private String polyline;

    @NotNull(message = "Rating cannot be null")
    @DecimalMin(value = "0.0", message = "Rating must be non-negative")
    @DecimalMax(value = "5.0", message = "Rating cannot be greater than 5")
    private BigDecimal rating;

    @NotNull(message = "Usage count cannot be null")
    @PositiveOrZero(message = "Usage count must be non-negative")
    private Integer usageCount;

    @NotNull(message = "Public status cannot be null")
    private Boolean isPublic;

    @NotNull(message = "Creation date cannot be null")
    private LocalDate createdAt;
}