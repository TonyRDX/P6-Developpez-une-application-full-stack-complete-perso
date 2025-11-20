package com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.dto.AddTopicRequest;
import com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.dto.SubscribeTopicRequest;
import com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.dto.UnsubscribeTopicRequest;
import com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.service.TopicService;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.Subscription;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.TopicPersistence;
import com.openclassrooms.mddapi.shared.infrastructure.MessageHandler;
import com.openclassrooms.mddapi.shared.infrastructure.dto.EmptyDto;
import com.openclassrooms.mddapi.shared.infrastructure.service.ReactiveUserContext;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "http://localhost:4200") 
public class TopicController {
    private final MessageHandler<AddTopicRequest, Mono<TopicPersistence>> addTopicHandler;
    private final MessageHandler<EmptyDto, Flux<TopicPersistence>> getAllTopicHandler;
    private final MessageHandler<SubscribeTopicRequest, Mono<Subscription>> subscribeTopicHandler;
    private final MessageHandler<UnsubscribeTopicRequest, Mono<Subscription>> unsubscribeTopicHandler;

    public TopicController(
        MessageHandler<AddTopicRequest, Mono<TopicPersistence>> addTopicHandler,
        MessageHandler<EmptyDto, Flux<TopicPersistence>> getAllTopicHandler,
        MessageHandler<SubscribeTopicRequest, Mono<Subscription>> subscribeTopicHandler,
        MessageHandler<UnsubscribeTopicRequest, Mono<Subscription>> unsubscribeTopicHandler
    ) {
        this.addTopicHandler = addTopicHandler;
        this.getAllTopicHandler = getAllTopicHandler;
        this.subscribeTopicHandler = subscribeTopicHandler;
        this.unsubscribeTopicHandler = unsubscribeTopicHandler;
    }

    @PostMapping
    public Mono<TopicPersistence> post(@RequestBody AddTopicRequest request) {
        return this.addTopicHandler.handle(request);
    }

    @GetMapping
    public Flux<TopicPersistence> get() {
        return getAllTopicHandler.handle(new EmptyDto());
    }

    @PutMapping("/{topicId}/subscription")
    public Mono<Subscription> subscribe(@PathVariable Integer topicId) {
        return subscribeTopicHandler.handle(new SubscribeTopicRequest(topicId));
    }

    @DeleteMapping("/{topicId}/subscription")
    public Mono<Subscription> unsubscribe(@PathVariable Integer topicId) {
        return unsubscribeTopicHandler.handle(new UnsubscribeTopicRequest(topicId));
    }
}
