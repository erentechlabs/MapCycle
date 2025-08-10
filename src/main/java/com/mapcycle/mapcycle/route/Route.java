package com.mapcycle.mapcycle.route;

import com.mapcycle.mapcycle.user.User;
import jakarta.persistence.*;
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
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "difficulty_level")
    private Integer difficultyLevel;

    @Column(name = "estimated_duration")
    private Integer estimatedDuration; // minutes

    @Column(precision = 10, scale = 2)
    private BigDecimal distance; // km

    @Column(name = "start_latitude", precision = 10, scale = 8, nullable = false)
    private BigDecimal startLatitude;

    @Column(name = "start_longitude", precision = 11, scale = 8, nullable = false)
    private BigDecimal startLongitude;

    @Column(name = "end_latitude", precision = 10, scale = 8)
    private BigDecimal endLatitude;

    @Column(name = "end_longitude", precision = 11, scale = 8)
    private BigDecimal endLongitude;

    @Column(columnDefinition = "TEXT")
    private String waypoints;

    @Column(columnDefinition = "TEXT")
    private String polyline;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "usage_count")
    private Integer usageCount;

    @Column(name = "is_public")
    private Boolean isPublic;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
}
