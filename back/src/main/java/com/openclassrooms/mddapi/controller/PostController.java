package com.openclassrooms.mddapi.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.sse.PostSse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200") 
public class PostController {

    private final PostService postService;

    public PostController(
        PostService postService, 
        Sinks.Many<PostSse> postSink
    ) {
        this.postService = postService;
        this.postSink = postSink;
    }

    @PostMapping
    public Mono<Post> post() {
        Post post = new Post();
        post.setTitle("ae");
        post.setContent("content");

        return postService.create(post)
                .doOnSuccess(saved -> {
                    // on pousse dans le flux SSE
                    PostSse event = new PostSse(saved.getId(), saved.getTitle(), saved.getContent());
                    postSink.tryEmitNext(event);
                });
    }


    private final Sinks.Many<PostSse> postSink;

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PostSse> streamPosts() {
        return postSink.asFlux();
    }
}
