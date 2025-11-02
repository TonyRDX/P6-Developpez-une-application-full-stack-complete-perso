package com.openclassrooms.mddapi.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Topic;

@Repository
public interface TopicRepository extends ReactiveCrudRepository<Topic, Integer> {
}
