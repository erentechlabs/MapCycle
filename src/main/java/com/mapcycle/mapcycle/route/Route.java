package com.mapcycle.mapcycle.route;

import com.mapcycle.mapcycle.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "routes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "difficulty_level")
    @Min(value = 1, message = "Difficulty level must be at least 1")
    @Max(value = 5, message = "Difficulty level must be at most 5")
    @Builder.Default
    private Integer difficultyLevel = 1;

    @Column(name = "estimated_duration")
    private Integer estimatedDuration; // in minutes

    @Column(precision = 10, scale = 2)
    private BigDecimal distance; // in kilometers

    @Column(name = "start_latitude", precision = 10, scale = 8)
    private BigDecimal startLatitude;

    @Column(name = "start_longitude", precision = 11, scale = 8)
    private BigDecimal startLongitude;

    @Column(name = "end_latitude", precision = 10, scale = 8)
    private BigDecimal endLatitude;

    @Column(name = "end_longitude", precision = 11, scale = 8)
    private BigDecimal endLongitude;

    @Column(columnDefinition = "TEXT")
    private String waypoints; // JSON array of coordinates

    @Column(columnDefinition = "TEXT")
    private String polyline; // Encoded polyline

    @Column(precision = 3, scale = 2)
    @Builder.Default
    private BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "usage_count")
    @Builder.Default
    private Integer usageCount = 0;

    @Column(name = "is_public")
    @Builder.Default
    private Boolean isPublic = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
}