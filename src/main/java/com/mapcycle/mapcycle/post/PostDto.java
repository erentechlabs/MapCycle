package com.mapcycle.mapcycle.post;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Positive(message = "Ride ID, if present, must be a positive number")
    private Long rideId;

    @NotBlank(message = "Caption cannot be blank")
    @Size(max = 2200, message = "Caption cannot be longer than 2200 characters")
    private String caption;

    @Size(max = 255, message = "Location name cannot be longer than 255 characters")
    private String locationName;

    @NotNull(message = "Latitude cannot be null")
    @DecimalMin(value = "-90.0", message = "Latitude must be greater than or equal to -90")
    @DecimalMax(value = "90.0", message = "Latitude must be less than or equal to 90")
    private BigDecimal latitude;

    @NotNull(message = "Longitude cannot be null")
    @DecimalMin(value = "-180.0", message = "Longitude must be greater than or equal to -180")
    @DecimalMax(value = "180.0", message = "Longitude must be less than or equal to 180")
    private BigDecimal longitude;

    @NotEmpty(message = "At least one image URL is required")
    @Size(max = 10, message = "A post can have a maximum of 10 images")
    private List<String> imageUrls;

    // These fields are typically calculated and not part of the creation request,
    // so they don't need validation.
    private Integer likesCount;
    private Integer commentsCount;

    private LocalDate createdAt;
}
