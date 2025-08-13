package com.mapcycle.mapcycle.models.dtos;

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
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private BigDecimal totalDistance;
    private Long totalTime;
    private Integer level;
    private Integer experiencePoints;
    private LocalDateTime createdAt;
    private boolean isActive;
}