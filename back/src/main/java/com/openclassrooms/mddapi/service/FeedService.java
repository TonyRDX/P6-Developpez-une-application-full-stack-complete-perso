package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.sse.PostPublisher;
import com.openclassrooms.mddapi.sse.PostSse;

import reactor.core.publisher.Flux;

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

    public Flux<PostSse> liveForUser(String userId) {
        return userService.getThemes(userId)
                .map(List::copyOf)
                .flatMapMany(themes ->
                        postPublisher.flux()
                                .filter(post -> themes.contains(post.theme()))
                );
    }
}
