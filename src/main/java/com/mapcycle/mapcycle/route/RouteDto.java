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

    @Null(message = "ID must be null when creating a route")
    private Long id;

    @NotNull(message = "Creator ID cannot be null")
    @Positive(message = "Creator ID must be positive")
    private Long createdById;

    private String createdByUsername;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;

    @NotNull(message = "Difficulty level is required")
    @Min(value = 1, message = "Difficulty must be at least 1")
    @Max(value = 5, message = "Difficulty must be at most 5")
    private Integer difficultyLevel;

    @Positive(message = "Estimated duration must be positive")
    private Integer estimatedDuration;

    @Positive(message = "Distance must be positive")
    private BigDecimal distance;

    @NotNull(message = "Start latitude is required")
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private BigDecimal startLatitude;

    @NotNull(message = "Start longitude is required")
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private BigDecimal startLongitude;

    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private BigDecimal endLatitude;

    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private BigDecimal endLongitude;

    private String waypoints;

    private String polyline;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private BigDecimal rating;

    @PositiveOrZero
    private Integer usageCount;

    @NotNull
    private Boolean isPublic;

    @Null(message = "Creation date is generated automatically")
    private LocalDate createdAt;
}
