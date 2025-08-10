package com.mapcycle.mapcycle.post;

import com.mapcycle.mapcycle.post.comment.CommentDto;
import com.mapcycle.mapcycle.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/feed")
    public ResponseEntity<Page<PostDto>> getFeed(Pageable pageable) {
        return ResponseEntity.ok(postService.getFeed(pageable));
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
                                              @AuthenticationPrincipal User currentUser) {
        PostDto createdPost = postService.createPost(postDto, currentUser);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id,
                                              @Valid @RequestBody PostDto postDto,
                                              @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(postService.updatePost(id, postDto, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,
                                           @AuthenticationPrincipal User currentUser) {
        postService.deletePost(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long id,
                                         @AuthenticationPrincipal User currentUser) {
        postService.toggleLike(id, currentUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getComments(id));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long id,
                                                    @Valid @RequestBody CommentDto commentDto,
                                                    @AuthenticationPrincipal User currentUser) {
        CommentDto createdComment = postService.createComment(id, commentDto, currentUser);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
}
