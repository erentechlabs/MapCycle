package com.mapcycle.mapcycle.ride;

import com.mapcycle.mapcycle.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JPA Entity class representing the 'rides' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "distance", precision = 10, scale = 2)
    private BigDecimal distance;

    @Column(name = "duration") // seconds
    private Long duration;

    @Column(name = "average_speed", precision = 6, scale = 2)
    private BigDecimal averageSpeed;

    @Column(name = "max_speed", precision = 6, scale = 2)
    private BigDecimal maxSpeed;

    @Column(name = "calories_burned")
    private Integer caloriesBurned;

    @Column(name = "elevation_gain", precision = 10, scale = 2)
    private BigDecimal elevationGain;

    @Column(name = "start_latitude", precision = 9, scale = 6)
    private BigDecimal startLatitude;

    @Column(name = "start_longitude", precision = 9, scale = 6)
    private BigDecimal startLongitude;

    @Column(name = "end_latitude", precision = 9, scale = 6)
    private BigDecimal endLatitude;

    @Column(name = "end_longitude", precision = 9, scale = 6)
    private BigDecimal endLongitude;

    @Column(name = "route_polyline", columnDefinition = "TEXT")
    private String routePolyline;

    @PastOrPresent
    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @PastOrPresent
    @Column(name = "finished_at", nullable = false)
    private LocalDateTime finishedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
}
