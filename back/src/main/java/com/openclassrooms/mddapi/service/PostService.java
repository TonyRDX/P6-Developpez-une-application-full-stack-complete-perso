package com.openclassrooms.mddapi.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.sse.PostPublisher;
import com.openclassrooms.mddapi.sse.PostSse;

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

    public Flux<Post> getRecent() {
        return postRepository.findRecent();
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
                            "java")
                    );
                });
    }

}
