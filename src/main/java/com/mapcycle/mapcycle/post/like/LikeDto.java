package com.mapcycle.mapcycle.post.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {

    private Long id;

    // request'te gelmezse authenticated principal kullanılıyor.
    private Long userId;

    private String username;

    private Long postId;

    private LocalDateTime createdAt;
}
