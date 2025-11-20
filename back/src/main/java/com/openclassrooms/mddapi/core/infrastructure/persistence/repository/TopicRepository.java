package com.openclassrooms.mddapi.core.infrastructure.persistence.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.TopicPersistence;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TopicRepository extends ReactiveCrudRepository<TopicPersistence, Integer> {
    Mono<TopicPersistence> findById(Integer id);
    @Query("""
        SELECT * FROM topics t
        LEFT JOIN subscriptions s 
            ON s.topic_id = t.id
            AND s.user_id = :userId
    """)
    Flux<TopicPersistence> findAllWithUserId(Integer userId);
    @Query("""
        SELECT * FROM topics t
        INNER JOIN subscriptions s 
            ON s.topic_id = t.id
            AND s.user_id = :userId
    """)
    Flux<TopicPersistence> findSubscribedByUserId(Integer userId);
    @Query("""
    SELECT * FROM topics t
    LEFT JOIN subscriptions s
        ON s.topic_id = t.id
        AND s.user_id = :userId
    WHERE t.id = :topicId
    """)
    Mono<TopicPersistence> findById(Integer topicId, Integer userId);
}
