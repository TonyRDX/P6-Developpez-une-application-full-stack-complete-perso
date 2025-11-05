package com.openclassrooms.mddapi.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.domain.model.Topic;

import reactor.core.publisher.Mono;



@Repository
public interface TopicRepository extends ReactiveCrudRepository<Topic, Integer> {
    Mono<Topic> findById(Integer id);
}
