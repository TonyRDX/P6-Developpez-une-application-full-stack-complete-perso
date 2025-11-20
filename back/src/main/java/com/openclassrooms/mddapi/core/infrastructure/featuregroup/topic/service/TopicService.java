package com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.service;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.Subscription;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.TopicPersistence;
import com.openclassrooms.mddapi.core.infrastructure.persistence.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.core.infrastructure.persistence.repository.TopicRepository;

import reactor.core.publisher.Mono;

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

    public Mono<TopicPersistence> getOne(Integer id) {
        return topicRepository.findById(id);
    }
}
