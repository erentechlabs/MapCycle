package com.mapcycle.mapcycle.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Please provide a valid email address.")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password field cannot be blank.")
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 50, message = "First name cannot be longer than 50 characters.")
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 50, message = "Last name cannot be longer than 50 characters.")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "role")
    private String role;

    @NotNull
    @Column(name = "total_distance", precision = 10, scale = 2)
    private BigDecimal totalDistance = BigDecimal.ZERO;

    @NotNull
    @Column(name = "total_time")
    private Long totalTime = 0L;

    @NotNull
    @Column(name = "level")
    private Integer level = 1;

    @NotNull
    @Column(name = "experience_points")
    private Integer experiencePoints = 0;

    @CreationTimestamp // Automatically adds a timestamp when the record is created.
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp // Automatically adds a timestamp when the record is updated.
    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    @Column(name = "is_active")
    private boolean isActive = true;
}
