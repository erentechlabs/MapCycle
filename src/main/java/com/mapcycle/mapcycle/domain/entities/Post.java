package com.mapcycle.mapcycle.domain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Assuming ride_id links to the Route entity created previously
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id")
    private Route ride;

    @Column(columnDefinition = "TEXT")
    private String caption;

    @Column(name = "location_name")
    private String locationName;

    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;

    @ElementCollection
    @CollectionTable(name = "post_image_urls", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "image_url", nullable = false)
    private List<String> imageUrls = new ArrayList<>();

    @ColumnDefault("0")
    @Column(name = "likes_count", nullable = false)
    private Integer likesCount = 0;

    @ColumnDefault("0")
    @Column(name = "comments_count", nullable = false)
    private Integer commentsCount = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    // Bidirectional relationship to Likes
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    // Bidirectional relationship to Comments
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}