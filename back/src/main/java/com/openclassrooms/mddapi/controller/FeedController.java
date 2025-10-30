package com.openclassrooms.mddapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.PostService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/feed")
@CrossOrigin(origins = "http://localhost:4200") 
public class FeedController {

    private final PostService postService;

    public FeedController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Flux<Post> getFeed() {
        return postService.getAllPosts();
    }
}
