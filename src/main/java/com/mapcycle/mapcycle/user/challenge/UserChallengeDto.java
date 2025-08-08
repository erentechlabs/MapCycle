package com.mapcycle.mapcycle.user.challenge;

import jakarta.validation.constraints.NotNull;
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
public class UserChallengeDto {

    private Long id;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Challenge ID cannot be null")
    private Long challengeId;

    @NotNull(message = "Current progress cannot be null")
    private BigDecimal currentProgress;

    private boolean isCompleted;

    private LocalDate completedAt;

    private LocalDate createdAt;
}
