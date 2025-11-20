package com.openclassrooms.mddapi.core.infrastructure.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.core.application.service.FeedService;
import com.openclassrooms.mddapi.core.application.usecase.getfeed.GetFeedQuery;
import com.openclassrooms.mddapi.core.infrastructure.dto.SinglePostFeed;
import com.openclassrooms.mddapi.core.infrastructure.service.ReactiveUserContext;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/feed")
@CrossOrigin(origins = "http://localhost:4200") 
public class FeedController {
    private final FeedService feedService;

    private final Flux<ServerSentEvent<SinglePostFeed>> heartbeat =
        Flux.interval(Duration.ofSeconds(15))
            .map(tick -> ServerSentEvent.<SinglePostFeed>builder()
                    .event("ping")
                    .comment("keep-alive")
                    .build()
            );

    @Autowired
    private ReactiveUserContext userContext;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public Flux<SinglePostFeed> getFeed() {
        return userContext.getUserId()
            .flatMapMany((userId) -> {
                GetFeedQuery getFeedQuery = new GetFeedQuery(
                    userId,
                    true
                );
                return this.feedService.getFeed(getFeedQuery);
            });
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<SinglePostFeed>> stream() {
        Mono<Integer> userIdMono = userContext.getUserId() 
            .switchIfEmpty(Mono.error(new IllegalStateException("No user id")));

        Flux<ServerSentEvent<SinglePostFeed>> posts = userIdMono
            .flatMapMany(feedService::liveForUser)
            .map(post ->
                    ServerSentEvent.<SinglePostFeed>builder()
                            .event("post")
                            .data(post)
                            .build()
                )
                .doOnSubscribe(sub -> System.out.println("info: [SSE] client connected"))
                .doOnCancel(() -> System.out.println("info: [SSE] client disconnected"))
                .doOnComplete(() -> System.out.println("warn: [SSE] stream completed (should NOT happen)"))
                .doOnError(err -> System.out.println("error: [SSE] stream error" + err.getMessage())
            );

        return Flux.merge(posts, heartbeat);
    }
}
