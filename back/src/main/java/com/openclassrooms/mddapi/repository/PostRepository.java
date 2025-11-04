package com.openclassrooms.mddapi.repository;

import java.util.Collection;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Post;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PostRepository extends ReactiveCrudRepository<Post, Integer> {
    @Query("SELECT * FROM posts ORDER BY created_at DESC LIMIT 30")
    Flux<Post> findRecent();
    Flux<Post> findAllByTopicIdIn(Collection<Integer> topicIds);
    Mono<Post> findById(Integer id);
}
