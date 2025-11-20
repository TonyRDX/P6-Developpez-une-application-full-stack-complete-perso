package com.openclassrooms.mddapi.core.infrastructure.persistence.repository;

import java.util.Collection;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.PostPersistence;

import reactor.core.publisher.Flux;

@Repository
public interface PostRepository extends ReactiveCrudRepository<PostPersistence, Integer> {
    @Query("SELECT * FROM posts ORDER BY created_at DESC LIMIT 30")
    Flux<PostPersistence> findRecent();
    Flux<PostPersistence> findAllByTopicIdIn(Collection<Integer> topicIds);
}
