package com.openclassrooms.mddapi.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.application.usecase.getfeed.GetFeedQuery;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostPublisher;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostSse;
import com.openclassrooms.mddapi.infrastructure.persistence.Post;
import com.openclassrooms.mddapi.infrastructure.repository.PostRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FeedService {

    private final PostPublisher postPublisher;
    private final UserService userService;
    private final PostRepository postRepository;

    public FeedService(
        PostPublisher postPublisher, 
        PostRepository postRepository, 
        UserService userService
    ) {
        this.postPublisher = postPublisher;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    public Flux<Post> getFeed(GetFeedQuery getFeedQuery) {
        return userService.getTopics(getFeedQuery.userId())
            .flatMapMany(ids ->
            ids.isEmpty() ?
                Flux.empty() : 
                postRepository.findAllByTopicIdIn(ids)
        );
    }

    public Flux<PostSse> liveForUser(Integer userId) {
        return userService.getTopics(userId)
                .map(List::copyOf)
                .flatMapMany(topics ->
                        postPublisher.flux()
                                .filter(post -> topics.contains(post.topicId()))
                );
    }
}
