package com.mapcycle.mapcycle.post;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @GetMapping("/freed")
    public String getFreed() {
        return "Get Freed";
    }

    @PostMapping
    public String createPost() {
        return "Endpoint for creating a new post";
    }

    @PutMapping("/{id}")
    public String updatePost(@PathVariable Long id) {
        return "Endpoint for updating post with ID: " + id;
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        return "Endpoint for deleting post with ID: " + id;
    }

    @PostMapping("/{id}/like")
    public String likePost(@PathVariable Long id) {
        return "Endpoint for liking post with ID: " + id;
    }

    @GetMapping("/{id}/comments")
    public String getComments(@PathVariable Long id) {
        return "Endpoint for getting comments for post with ID: " + id;
    }

    @PostMapping("/{id}/comments")
    public String createComment(@PathVariable Long id) {
        return "Endpoint for creating a comment on post with ID: " + id;
    }
}
