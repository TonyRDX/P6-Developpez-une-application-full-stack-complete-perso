package com.openclassrooms.mddapi.application.service;

import java.time.Instant;

import com.openclassrooms.mddapi.domain.model.Topic;
import com.openclassrooms.mddapi.infrastructure.repository.TopicRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(
        TopicRepository topicRepository
    ) {
        this.topicRepository = topicRepository;
    }

    public Mono<Topic> getOne(Integer id) {
        return topicRepository.findById(id);
    }

    public Flux<Topic> getAll() {
        return topicRepository.findAll();
    }

    public Mono<Topic> create(Topic topic) {
        topic.setCreatedAt(Instant.now());
        return topicRepository.save(topic);
    }

}
