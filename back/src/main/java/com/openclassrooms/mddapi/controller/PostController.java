package com.openclassrooms.mddapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.AddPostRequest;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.TopicService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200") 
public class PostController {

    private final PostService postService;
    private final TopicService topicService;

    public PostController(
        PostService postService,    
        TopicService topicService    
    ) {
        this.postService = postService;
        this.topicService = topicService;
    }

    @PostMapping
    public Mono<Post> post(@RequestBody AddPostRequest request) {
        Post post = new Post();
        post.setTitle(request.title());
        post.setContent(request.content());
        post.setTopicId(request.topic_id());
        
        return topicService.getOne(request.topic_id())
            .switchIfEmpty(Mono.error(new RuntimeException("Topic not found")))
            .flatMap(topic -> postService.create(post));
    }

    @GetMapping("/{id}")
    public Mono<Post> get(@PathVariable Integer id) {
        return postService.getOne(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Failed to fetch")));
    }
}
