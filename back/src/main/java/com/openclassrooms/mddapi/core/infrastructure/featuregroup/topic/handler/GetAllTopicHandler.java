package com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.handler;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.TopicPersistence;
import com.openclassrooms.mddapi.core.infrastructure.persistence.repository.TopicRepository;
import com.openclassrooms.mddapi.shared.infrastructure.MessageHandler;
import com.openclassrooms.mddapi.shared.infrastructure.dto.EmptyDto;
import com.openclassrooms.mddapi.shared.infrastructure.service.ReactiveUserContext;

import reactor.core.publisher.Flux;

@Component
public class GetAllTopicHandler implements MessageHandler<EmptyDto, Flux<TopicPersistence>> {
    private final TopicRepository topicRepository;
    private final ReactiveUserContext userContext;

    public GetAllTopicHandler(
        ReactiveUserContext userContext,
        TopicRepository topicRepository
    ) {
        this.userContext = userContext;
        this.topicRepository = topicRepository;
    }

    @Override
    public Flux<TopicPersistence> handle(EmptyDto param) {
        return userContext.getUserId().flatMapMany(userId -> 
            topicRepository.findAllWithUserId(userId)
        );
    }
}
