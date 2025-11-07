package com.openclassrooms.mddapi.infrastructure.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.application.service.FeedService;
import com.openclassrooms.mddapi.application.service.PostService;
import com.openclassrooms.mddapi.application.service.UserService;
import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostSse;
import com.openclassrooms.mddapi.infrastructure.service.ReactiveUserContext;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/feed")
@CrossOrigin(origins = "http://localhost:4200") 
public class FeedController {
    private final PostService postService;
    private final FeedService feedService;

    private final Flux<ServerSentEvent<PostSse>> heartbeat =
        Flux.interval(Duration.ofSeconds(15))
            .map(tick -> ServerSentEvent.<PostSse>builder()
                    .event("ping")
                    .comment("keep-alive")
                    .build()
            );

    @Autowired
    private ReactiveUserContext userContext;
    @Autowired
    private UserService userService;

    public FeedController(PostService postService, FeedService feedService) {
        this.postService = postService;
        this.feedService = feedService;
    }

    @GetMapping
    public Flux<Post> getFeed() {
        Mono<Integer> userIdMono = userContext.getUserId();
        return this.postService.getRecent(userService.getTopics(userIdMono));
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<PostSse>> stream() {
        Mono<Integer> userIdMono = userContext.getUserId();
        Flux<ServerSentEvent<PostSse>> posts = feedService.liveForUser(userIdMono)
            .map(post ->
                    ServerSentEvent.<PostSse>builder()
                            .event("post")
                            .data(post)
                            .build()
            );
 
        return Flux.merge(posts, heartbeat);
    }
}
