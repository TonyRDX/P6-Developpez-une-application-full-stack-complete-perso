package com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.handler;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.dto.UnsubscribeTopicRequest;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.Subscription;
import com.openclassrooms.mddapi.core.infrastructure.persistence.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.shared.infrastructure.MessageHandler;
import com.openclassrooms.mddapi.shared.infrastructure.service.ReactiveUserContext;

import reactor.core.publisher.Mono;

@Component
public class UnsubscribeTopicHandler implements MessageHandler<UnsubscribeTopicRequest, Mono<Subscription>> {
    private final SubscriptionRepository subscriptionRepository;
    private final ReactiveUserContext userContext;

    public UnsubscribeTopicHandler(
        ReactiveUserContext userContext,
        SubscriptionRepository subscriptionRepository
    ) {
        this.userContext = userContext;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Mono<Subscription> handle(UnsubscribeTopicRequest msg) {
        return userContext.getUserId().flatMap(userId -> 
            subscriptionRepository.deleteByTopicIdAndUserId(msg.topicId(), userId)
        );
    }
}
