package com.openclassrooms.mddapi.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.FeedService;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.sse.PostSse;

import reactor.core.publisher.Flux;

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

    public FeedController(PostService postService, FeedService feedService) {
        this.postService = postService;
        this.feedService = feedService;
    }

    @GetMapping
    public Flux<Post> getFeed() {
        return this.postService.getRecent();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<PostSse>> stream() {
        String userId = "u1";
        Flux<ServerSentEvent<PostSse>> posts = feedService.liveForUser(userId)
            .map(post ->
                    ServerSentEvent.<PostSse>builder()
                            .event("post")
                            .data(post)
                            .build()
            );

        return Flux.merge(posts, heartbeat);
    }
}
