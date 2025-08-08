package com.mapcycle.mapcycle.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    private String username;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
    private String password;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @Size(max = 50, message = "First name cannot be longer than 50 characters.")
    private String firstName;

    @Size(max = 50, message = "Last name cannot be longer than 50 characters.")
    private String lastName;

    private String profileImageUrl;

    private String role;

    @NotNull
    private BigDecimal totalDistance;

    @NotNull
    private Long totalTime;

    @NotNull
    private Integer level;

    @NotNull
    private Integer experiencePoints;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private boolean isActive;
}
