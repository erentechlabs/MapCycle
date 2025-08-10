package com.mapcycle.mapcycle.achievement;

import com.mapcycle.mapcycle.user.achievement.AchievementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchievementDto {

    private Long id; // Create işlemlerinde null olabilir, bu yüzden @NotNull kaldırıldı.

    @NotBlank(message = "Achievement name cannot be blank.")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank.")
    private String description;

    private String iconUrl;

    @NotNull(message = "Achievement type must be specified.")
    private AchievementType achievementType;

    @NotNull(message = "Requirement value must be specified.")
    @Positive(message = "Requirement value must be a positive number.")
    private BigDecimal requirementValue;

    @NotNull(message = "Points value must be specified.")
    @Positive(message = "Points value must be a positive number.")
    private Integer points;

    @NotNull(message = "Creation date cannot be null")
    private LocalDateTime createdAt;
}
