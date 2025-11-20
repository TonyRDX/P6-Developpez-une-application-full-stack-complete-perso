package com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.service;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.Subscription;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.TopicPersistence;
import com.openclassrooms.mddapi.core.infrastructure.persistence.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.core.infrastructure.persistence.repository.TopicRepository;

import reactor.core.publisher.Flux;
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

    public Flux<TopicPersistence> getAll(Integer userId) {
        return topicRepository.findAllWithUserId(userId);
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
