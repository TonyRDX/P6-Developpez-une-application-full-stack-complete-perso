package com.openclassrooms.mddapi.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostPublisher;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostSse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FeedService {

    private final PostPublisher postPublisher;
    private final UserService userService;

    public FeedService(
        PostPublisher postPublisher, 
        UserService userService
    ) {
        this.postPublisher = postPublisher;
        this.userService = userService;
    }

    public Flux<PostSse> liveForUser(Mono<Integer> userId) {
        return userService.getTopics(userId)
                .map(List::copyOf)
                .flatMapMany(topics ->
                        postPublisher.flux()
                                .filter(post -> topics.contains(post.topicId()))
                );
    }
}
