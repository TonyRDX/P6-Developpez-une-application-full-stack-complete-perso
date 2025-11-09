package com.openclassrooms.mddapi.application.service;

import java.time.Instant;

import com.openclassrooms.mddapi.domain.model.Topic;
import com.openclassrooms.mddapi.infrastructure.repository.TopicRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.mddapi.infrastructure.persistence.Subscription;
import com.openclassrooms.mddapi.infrastructure.repository.SubscriptionRepository;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final SubscriptionRepository subscriptionRepository;

    public TopicService(
        TopicRepository topicRepository,
        SubscriptionRepository subscriptionRepository
    ) {
        this.topicRepository = topicRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public Mono<Topic> getOne(Integer id) {
        return topicRepository.findById(id);
    }

    public Flux<Topic> getAll(Integer userId) {
        return topicRepository.findAllWithUserId(userId);
    }

    public Mono<Topic> create(Topic topic) {
        topic.setCreatedAt(Instant.now());
        return topicRepository.save(topic);
    }

    public Mono<Subscription> subscribe(Integer topicId, Integer userId) {
        return subscriptionRepository.findByTopicIdAndUserId(topicId, userId)
            .switchIfEmpty(Mono.defer(() -> {
                    Subscription sub = new Subscription();
                    sub.setTopicId(topicId);
                    sub.setUserId(userId);
                    return subscriptionRepository.save(sub);
            }));
    }

    public Mono<Subscription> unsubscribe(Integer topicId, Integer userId) {
        return subscriptionRepository.deleteByTopicIdAndUserId(topicId, userId);
    }
}
