package com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.handler;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.dro.AddTopicRequest;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.TopicPersistence;
import com.openclassrooms.mddapi.core.infrastructure.persistence.repository.TopicRepository;
import com.openclassrooms.mddapi.shared.infrastructure.MessageHandler;

import reactor.core.publisher.Mono;

@Component
public class CreateTopicHandler implements MessageHandler<AddTopicRequest, Mono<TopicPersistence>> {
    private final TopicRepository topicRepository;

    public CreateTopicHandler(
        TopicRepository topicRepository
    ) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Mono<TopicPersistence> handle(AddTopicRequest msg) {
        TopicPersistence topic = new TopicPersistence();
        topic.setTitle(msg.title());
        topic.setContent(msg.content());
        topic.setCreatedAt(Instant.now());
        return topicRepository.save(topic);
    }
}
