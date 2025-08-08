package com.mapcycle.mapcycle.user.achievement;


import com.mapcycle.mapcycle.achievement.Achievement;
import com.mapcycle.mapcycle.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

/**
 * JPA Entity class representing the 'user_achievements' table.
 * It links users to the achievements they have earned.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_achievements",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "achievement_id"})
        }
)
public class UserAchievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_id", nullable = false)
    private Achievement achievement;

    @CreationTimestamp
    @Column(name = "earned_at", updatable = false, nullable = false)
    private LocalDate earnedAt;
}

