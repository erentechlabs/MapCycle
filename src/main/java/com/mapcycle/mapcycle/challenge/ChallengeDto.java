package com.mapcycle.mapcycle.challenge;

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
public class ChallengeDto {

    private Long id;

    @NotBlank(message = "Title cannot be blank.")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    private String description;

    @NotNull(message = "Challenge type must be specified.")
    private ChallengeType challengeType;

    @NotNull(message = "Target value must be specified.")
    @Positive(message = "Target value must be positive.")
    private BigDecimal targetValue;

    @NotNull(message = "Reward points must be specified.")
    @Positive(message = "Reward points must be positive.")
    private Integer rewardPoints;

    private Long rewardBadgeId;

    @NotNull(message = "Start date must be specified.")
    private LocalDate startDate;

    @NotNull(message = "End date must be specified.")
    @FutureOrPresent(message = "End date must be in the present or future.")
    private LocalDate endDate;

    private boolean isActive;

    private LocalDate createdAt;
}
