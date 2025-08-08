package com.mapcycle.mapcycle.user.social;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowerDto {

    private Long id;

    @NotNull(message = "Follower ID cannot be null")
    @Positive(message = "Follower ID must be a positive number")
    private Long followerId;

    @NotBlank(message = "Follower username cannot be blank")
    @Size(min = 3, max = 50, message = "Follower username must be between 3 and 50 characters")
    private String followerUsername;

    @NotNull(message = "Following ID cannot be null")
    @Positive(message = "Following ID must be a positive number")
    private Long followingId;

    @NotBlank(message = "Following username cannot be blank")
    @Size(min = 3, max = 50, message = "Following username must be between 3 and 50 characters")
    private String followingUsername;

    private LocalDate createdAt;
}