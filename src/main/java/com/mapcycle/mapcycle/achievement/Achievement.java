package com.mapcycle.mapcycle.achievement;

import com.mapcycle.mapcycle.user.achievement.AchievementType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * JPA Entity class representing the 'achievements' table.
 * Contains the definition and requirements for all achievements in the system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "achievements")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Achievement name cannot be blank.")
    @Size(max = 255)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Description cannot be blank.")
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "icon_url")
    private String iconUrl;

    @NotNull(message = "Achievement type must be specified.")
    @Enumerated(EnumType.STRING) // Ensures the enum is stored as a String in the DB.
    @Column(name = "achievement_type", nullable = false)
    private AchievementType achievementType;

    @NotNull(message = "Requirement value must be specified.")
    @Positive(message = "Requirement value must be a positive number.")
    @Column(name = "requirement_value", nullable = false)
    private BigDecimal requirementValue;

    @NotNull(message = "Points value must be specified.")
    @Positive(message = "Points value must be a positive number.")
    @Column(name = "points", nullable = false)
    private Integer points;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDate createdAt;
}