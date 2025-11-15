package com.openclassrooms.mddapi.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.application.usecase.getfeed.GetFeedQuery;
import com.openclassrooms.mddapi.domain.model.Topic;
import com.openclassrooms.mddapi.domain.model.User;
import com.openclassrooms.mddapi.infrastructure.dto.SinglePostFeed;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostPublisher;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostSse;
import com.openclassrooms.mddapi.infrastructure.persistence.Post;
import com.openclassrooms.mddapi.infrastructure.repository.PostRepository;
import com.openclassrooms.mddapi.infrastructure.repository.TopicRepository;
import com.openclassrooms.mddapi.infrastructure.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FeedService {
    private final PostPublisher postPublisher;
    private final UserService userService;
    private final TopicService topicService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public FeedService(
        PostPublisher postPublisher, 
        PostRepository postRepository, 
        UserRepository userRepository, 
        TopicRepository topicRepository, 
        UserService userService,
        TopicService topicService
    ) {
        this.postPublisher = postPublisher;
        this.userService = userService;
        this.topicService = topicService;
        this.postRepository = postRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public Flux<SinglePostFeed> getFeed(GetFeedQuery getFeedQuery) {
        return topicRepository.findSubscribedByUserId(getFeedQuery.userId())
            .map(Topic::getId)
            .collectList()
            .flatMapMany(ids -> 
                ids.isEmpty()
                    ? Flux.empty()
                    : postRepository.findAllByTopicIdIn(ids)
            )
            .flatMap(this::toSinglePostFeed);
    }

    public Flux<SinglePostFeed> liveForUser(Integer userId) {
        return topicRepository.findSubscribedByUserId(userId)
                .map(Topic::getId)
                .collectList()
                .flatMapMany(topics ->
                        postPublisher.flux()
                                .filter(post -> topics.contains(post.topic_id()))
                )
                .flatMap(postSse -> {
                    return this.toSinglePostFeed(postSse);
                });
    }

    private Mono<SinglePostFeed> toSinglePostFeed(PostSse el) {
        Mono<Post> postMono = postRepository.findById(el.id());
        Mono<User> userMono = userRepository.findById(el.author_id());
        
        Mono<SinglePostFeed> result = postMono.zipWith(userMono, (post, user) ->
            this.toSinglePostFeed(post, user)
        );

        return result;
    }

    private Mono<SinglePostFeed> toSinglePostFeed(Post post) {
        return userRepository.findById(post.getAuthorId())
            .map((user) -> this.toSinglePostFeed(post, user));
    }

    private SinglePostFeed toSinglePostFeed(Post post, User user) {
        return new SinglePostFeed(
                post.getId(), 
                post.getTitle(), 
                post.getContent(), 
                user.getName(), 
                post.getCreatedAt(), 
                post.getTopicId()
        );
    }
}
