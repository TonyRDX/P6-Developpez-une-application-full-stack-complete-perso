package com.openclassrooms.mddapi.application.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.infrastructure.repository.PostRepository;
import com.openclassrooms.mddapi.infrastructure.sse.PostPublisher;
import com.openclassrooms.mddapi.infrastructure.sse.PostSse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostPublisher postPublisher;

    public PostService(
        PostRepository postRepository,
        PostPublisher postPublisher
    ) {
        this.postRepository = postRepository;
        this.postPublisher = postPublisher;
    }

    public Flux<Post> getRecent(Mono<List<Integer>> topicIds) {
        return topicIds.flatMapMany(ids ->
            ids.isEmpty() ?
                Flux.empty() : 
                postRepository.findAllByTopicIdIn(ids)
        );
    }

    public Mono<Post> getOne(Integer id) {
        return postRepository.findById(id);
    }

    public Mono<Post> create(Post post) {
        post.setCreatedAt(Instant.now());
        return postRepository.save(post)
                .doOnSuccess(saved -> {
                    this.postPublisher.publish(
                        new PostSse(
                            saved.getId(), 
                            saved.getTitle(), 
                            saved.getContent(), 
                            post.getTopicId())
                    );
                });
    }

}
