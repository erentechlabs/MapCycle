package com.mapcycle.mapcycle.domain.entities;

import com.mapcycle.mapcycle.domain.enums.ChallengeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "challenges")
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank.")
    @Column(nullable = false, unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Challenge type must be specified.")
    @Enumerated(EnumType.STRING)
    @Column(name = "challenge_type", nullable = false)
    private ChallengeType challengeType;

    @NotNull(message = "Target value must be specified.")
    @Positive(message = "Target value must be positive.")
    @Column(name = "target_value", nullable = false)
    private BigDecimal targetValue;

    @NotNull(message = "Reward points must be specified.")
    @Positive(message = "Reward points must be positive.")
    @Column(name = "reward_points", nullable = false)
    private Integer rewardPoints;

    @Column(name = "reward_badge_id")
    private Long rewardBadgeId;

    @NotNull(message = "Start date must be specified.")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End date must be specified.")
    @FutureOrPresent(message = "End date must be in the present or future.")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_active")
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
}