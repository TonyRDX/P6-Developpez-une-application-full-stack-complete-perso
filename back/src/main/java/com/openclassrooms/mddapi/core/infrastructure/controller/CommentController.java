package com.openclassrooms.mddapi.core.infrastructure.controller;

import java.time.Instant;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.core.application.service.CommentService;
import com.openclassrooms.mddapi.core.infrastructure.dto.AddCommentRequest;
import com.openclassrooms.mddapi.core.infrastructure.dto.CommentResponse;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.Comment;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.User;
import com.openclassrooms.mddapi.core.infrastructure.persistence.repository.CommentRepository;
import com.openclassrooms.mddapi.core.infrastructure.persistence.repository.UserRepository;
import com.openclassrooms.mddapi.core.infrastructure.service.ReactiveUserContext;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@CrossOrigin(origins = "http://localhost:4200") 
public class CommentController {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReactiveUserContext userContext;

    public CommentController(
        CommentService commentService, 
        CommentRepository commentRepository,
        UserRepository userRepository,
        ReactiveUserContext userContext
    ) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.userContext = userContext;
    }

    @GetMapping
    public Flux<CommentResponse> get(@PathVariable Integer postId) {
        return commentRepository.findAllByPostId(postId)
            .flatMap(comment -> 
                userRepository.findById(comment.getUserId())  
                    .map(user -> toResponse(comment, user))
            );
    }

    public CommentResponse toResponse(Comment comment, User user) {
        return new CommentResponse(
            comment.getContent(),
            comment.getCreatedAt(),
            user.getName()
        );
    }

    @PostMapping
    public Mono<Comment> set(@RequestBody AddCommentRequest request, @PathVariable Integer postId) {
        return this.userContext.getUserId()
            .flatMap(userId -> {
                Comment newComment = new Comment();
                newComment.setContent(request.content());
                newComment.setPostId(postId);
                newComment.setCreatedAt(Instant.now());
                newComment.setUserId(userId);
                return commentRepository.save(newComment);
            });
    }
}
