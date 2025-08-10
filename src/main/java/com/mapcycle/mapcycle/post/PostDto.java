package com.mapcycle.mapcycle.post;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    private String username;

    private Long rideId;

    @NotBlank(message = "Caption cannot be blank")
    @Size(max = 2200, message = "Caption cannot be longer than 2200 characters")
    private String caption;

    @Size(max = 255, message = "Location name cannot be longer than 255 characters")
    private String locationName;

    private BigDecimal latitude;

    private BigDecimal longitude;

    @NotEmpty(message = "At least one image URL is required")
    @Size(max = 10, message = "A post can have a maximum of 10 images")
    private List<String> imageUrls;

    private Integer likesCount;
    private Integer commentsCount;

    private LocalDateTime createdAt;
}
