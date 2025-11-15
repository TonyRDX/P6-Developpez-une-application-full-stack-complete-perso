package com.openclassrooms.mddapi.infrastructure.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.domain.model.Topic;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TopicRepository extends ReactiveCrudRepository<Topic, Integer> {
    Mono<Topic> findById(Integer id);
    @Query("""
        SELECT * FROM topics t
        LEFT JOIN subscriptions s 
            ON s.topic_id = t.id
            AND s.user_id = :userId
    """)
    Flux<Topic> findAllWithUserId(Integer userId);
    @Query("""
        SELECT * FROM topics t
        INNER JOIN subscriptions s 
            ON s.topic_id = t.id
            AND s.user_id = :userId
    """)
    Flux<Topic> findSubscribedByUserId(Integer userId);
    @Query("""
    SELECT * FROM topics t
    LEFT JOIN subscriptions s
        ON s.topic_id = t.id
        AND s.user_id = :userId
    WHERE t.id = :topicId
    """)
    Mono<Topic> findById(Integer topicId, Integer userId);
}
