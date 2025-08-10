package com.mapcycle.mapcycle.post.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    @NotNull(message = "Post ID cannot be null")
    @Positive(message = "Post ID must be a positive number")
    private Long postId;

    // userId may not be supplied by the client when creating a comment
    // because we typically take it from the authenticated principal.
    private Long userId;

    // Username is sent in responses; not required in create requests.
    private String username;

    @NotBlank(message = "Content cannot be blank")
    @Size(max = 1000, message = "Comment cannot exceed 1000 characters")
    private String content;

    private LocalDateTime createdAt;
}
