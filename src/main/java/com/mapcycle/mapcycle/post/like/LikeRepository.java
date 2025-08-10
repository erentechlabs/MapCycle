package com.mapcycle.mapcycle.post.like;

import com.mapcycle.mapcycle.post.Post;
import com.mapcycle.mapcycle.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
}
