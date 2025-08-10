package com.mapcycle.mapcycle.user.challenge;


import com.mapcycle.mapcycle.achievement.challenge.Challenge;
import com.mapcycle.mapcycle.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * JPA Entity class representing the 'user_challenges' table.
 * It holds the status and progress of users in challenges.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_challenges",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "challenge_id"})
        }
)
public class UserChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @ColumnDefault("0.0")
    @Column(name = "current_progress", nullable = false)
    private BigDecimal currentProgress = BigDecimal.ZERO;

    @ColumnDefault("false")
    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false;

    @Column(name = "completed_at") // Can be nullable, as it's set upon completion
    private LocalDate completedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDate createdAt;
}