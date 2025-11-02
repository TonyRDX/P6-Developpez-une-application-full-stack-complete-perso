package com.openclassrooms.mddapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.AddPostRequest;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.PostService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200") 
public class PostController {

    private final PostService postService;

    public PostController(
        PostService postService    ) {
        this.postService = postService;
    }

    @PostMapping
    public Mono<Post> post(@RequestBody AddPostRequest request) {
        Post post = new Post();
        post.setTitle(request.title());
        post.setContent(request.content());

        return postService.create(post);
    }
}
