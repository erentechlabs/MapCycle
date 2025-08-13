package com.mapcycle.mapcycle.models.entities;

import com.mapcycle.mapcycle.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @NotBlank(message = "Kullanıcı adı boş olamaz.")
    @Size(min = 3, max = 50, message = "Kullanıcı adı 3 ile 50 karakter arasında olmalıdır.")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank(message = "E-posta boş olamaz.")
    @Email(message = "Lütfen geçerli bir e-posta adresi girin.")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Parola alanı boş olamaz.")
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 50, message = "İsim 50 karakterden uzun olamaz.")
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 50, message = "Soyisim 50 karakterden uzun olamaz.")
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Builder.Default
    @Column(name = "role", nullable = false, length = 50)
    private String role = "ROLE_USER";

    @NotNull
    @Builder.Default
    @Column(name = "total_distance", precision = 10, scale = 2)
    private BigDecimal totalDistance = BigDecimal.ZERO;

    @NotNull
    @Builder.Default
    @Column(name = "total_time")
    private Long totalTime = 0L;

    @NotNull
    @Builder.Default
    @Column(name = "level")
    private Integer level = 1;

    @NotNull
    @Builder.Default
    @Column(name = "experience_points")
    private Integer experiencePoints = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Ride> rides = new ArrayList<>();

    @OneToMany(mappedBy = "creatingUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Route> createdRoutes = new ArrayList<>();

    @Builder.Default
    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive = true;

    @PrePersist
    void prePersist() {
        if (isActive == null) {
            isActive = true;
        }
        if (totalDistance == null) totalDistance = BigDecimal.ZERO;
        if (totalTime == null) totalTime = 0L;
        if (level == null) level = 1;
        if (experiencePoints == null) experiencePoints = 0;
        if (role == null) role = "ROLE_USER";
    }
}
