package com.openclassrooms.mddapi.service;

import java.time.Instant;

import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.sse.PostEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository,
                       Sinks.Many<PostEvent> postSink) {
        this.postRepository = postRepository;
        this.postSink = postSink;
    }

    public Flux<Post> getAllPosts() {
        return postRepository.findAll();
    }

    private final Sinks.Many<PostEvent> postSink;

    public Mono<Post> create(Post post) {
        post.setCreatedAt(Instant.now());
        return postRepository.save(post)
                .doOnSuccess(saved -> {
                    postSink.tryEmitNext(
                        new PostEvent("CREATED", saved.getId(), Instant.now())
                    );
                });
    }

}
