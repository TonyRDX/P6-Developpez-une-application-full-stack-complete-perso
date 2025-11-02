package com.openclassrooms.mddapi.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(
        TopicRepository topicRepository
    ) {
        this.topicRepository = topicRepository;
    }

    public Flux<Topic> getAll() {
        return topicRepository.findAll();
    }

    public Mono<Topic> create(Topic topic) {
        topic.setCreatedAt(Instant.now());
        return topicRepository.save(topic);
    }

}
