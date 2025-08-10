package com.mapcycle.mapcycle.post;

import com.mapcycle.mapcycle.post.comment.Comment;
import com.mapcycle.mapcycle.post.comment.CommentDto;
import com.mapcycle.mapcycle.post.comment.CommentMapper;
import com.mapcycle.mapcycle.post.comment.CommentRepository;
import com.mapcycle.mapcycle.post.like.Like;
import com.mapcycle.mapcycle.post.like.LikeRepository;
import com.mapcycle.mapcycle.route.RouteRepository;
import com.mapcycle.mapcycle.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final RouteRepository routeRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public Page<PostDto> getFeed(Pageable pageable) {
        return postRepository.findAll(pageable).map(postMapper::toDto);
    }

    @Transactional
    public PostDto createPost(PostDto postDto, User currentUser) {
        Post post = postMapper.toEntity(postDto);
        post.setUser(currentUser);

        if (postDto.getRideId() != null) {
            routeRepository.findById(postDto.getRideId()).ifPresent(post::setRide);
        }

        Post savedPost = postRepository.save(post);
        return postMapper.toDto(savedPost);
    }

    @Transactional
    public PostDto updatePost(Long postId, PostDto postDto, User currentUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to update this post.");
        }

        post.setCaption(postDto.getCaption());
        post.setLocationName(postDto.getLocationName());
        post.setLatitude(postDto.getLatitude());
        post.setLongitude(postDto.getLongitude());
        if (postDto.getImageUrls() != null) {
            post.setImageUrls(postDto.getImageUrls());
        }

        Post updatedPost = postRepository.save(post);
        return postMapper.toDto(updatedPost);
    }

    @Transactional
    public void deletePost(Long postId, User currentUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to delete this post.");
        }

        postRepository.delete(post);
    }

    @Transactional
    public void toggleLike(Long postId, User currentUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        Optional<Like> existing = likeRepository.findByUserAndPost(currentUser, post);
        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            post.setLikesCount(Math.max(0, (post.getLikesCount() == null ? 0 : post.getLikesCount()) - 1));
        } else {
            Like newLike = Like.builder().user(currentUser).post(post).build();
            likeRepository.save(newLike);
            post.setLikesCount((post.getLikesCount() == null ? 0 : post.getLikesCount()) + 1);
        }
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getComments(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException("Post not found with id: " + postId);
        }
        return commentRepository.findByPostId(postId).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto createComment(Long postId, CommentDto commentDto, User currentUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        Comment comment = commentMapper.toEntity(commentDto);
        comment.setPost(post);
        comment.setUser(currentUser);

        // Güncelleme: comment sayacını güvenli güncelle
        post.setCommentsCount((post.getCommentsCount() == null ? 0 : post.getCommentsCount()) + 1);
        postRepository.save(post);

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }
}
