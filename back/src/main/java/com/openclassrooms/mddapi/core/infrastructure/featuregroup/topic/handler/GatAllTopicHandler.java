package com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.handler;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.TopicPersistence;
import com.openclassrooms.mddapi.core.infrastructure.persistence.repository.TopicRepository;
import com.openclassrooms.mddapi.shared.infrastructure.MessageHandler;

import reactor.core.publisher.Flux;

@Component
public class GatAllTopicHandler implements MessageHandler<Integer, Flux<TopicPersistence>> {
    private final TopicRepository topicRepository;

    public GatAllTopicHandler(
        TopicRepository topicRepository
    ) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Flux<TopicPersistence> handle(Integer param) {
        TopicPersistence topic = new TopicPersistence();
        throw new RuntimeException("null");
    }
}
