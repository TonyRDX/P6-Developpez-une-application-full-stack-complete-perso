package com.openclassrooms.mddapi.core.infrastructure.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.Subscription;

import reactor.core.publisher.Mono;

@Repository
public interface SubscriptionRepository extends ReactiveCrudRepository<Subscription, Integer> {
    Mono<Subscription> findByTopicIdAndUserId(Integer topicId, Integer userId);
    Mono<Subscription> deleteByTopicIdAndUserId(Integer topicId, Integer userId);
}
