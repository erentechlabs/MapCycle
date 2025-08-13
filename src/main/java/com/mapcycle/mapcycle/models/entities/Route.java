package com.mapcycle.mapcycle.models.entities;// package com.mapcycle.mapcycle.models.entities;

import com.mapcycle.mapcycle.models.enums.DifficultyLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The route name cannot be empty.")
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "The difficulty level must be specified.")
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level", nullable = false, length = 20)
    private DifficultyLevel difficultyLevel;

    @NotNull(message = "The estimated distance cannot be empty.")
    @Positive(message = "The distance must be a positive value.")
    @Column(name = "estimated_distance_km", nullable = false)
    private Double estimatedDistanceKm;

    @Column(name = "estimated_time_in_minutes")
    private Integer estimatedTimeInMinutes;

    @NotBlank(message = "The route data cannot be empty.")
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "route_data", columnDefinition = "jsonb")
    private String routeData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creating_user_id")
    private User creatingUser;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;
}