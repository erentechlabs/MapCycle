package com.mapcycle.mapcycle.models.dtos;

import com.mapcycle.mapcycle.models.enums.DifficultyLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteDto {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    private DifficultyLevel difficultyLevel;

    private Double estimatedDistanceKm;

    private Integer estimatedTimeInMinutes;

    private String routeData;

    private LocalDateTime creationDate;

    private Long creatingUserId;

}
