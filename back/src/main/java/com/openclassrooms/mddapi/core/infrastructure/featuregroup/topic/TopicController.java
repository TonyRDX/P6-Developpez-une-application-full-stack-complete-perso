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

import com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.dro.AddTopicRequest;
import com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.service.TopicService;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.TopicPersistence;
import com.openclassrooms.mddapi.shared.infrastructure.MessageHandler;
import com.openclassrooms.mddapi.shared.infrastructure.service.ReactiveUserContext;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "http://localhost:4200") 
public class TopicController {
    private final TopicService topicService;
    private final MessageHandler<AddTopicRequest, Mono<TopicPersistence>> addTopicHandler;
    // private final MessageHandler<null, Flux<TopicPersistence>> getAllTopicHandler;
    @Autowired
    private ReactiveUserContext userContext;

    public TopicController(
        TopicService topicService,
        MessageHandler<AddTopicRequest, Mono<TopicPersistence>> addTopicHandler
    ) {
        this.topicService = topicService;
        this.addTopicHandler = addTopicHandler;
    }

    @PostMapping
    public Mono<TopicPersistence> post(@RequestBody AddTopicRequest request) {
        return this.addTopicHandler.handle(request);
    }

    @GetMapping
    public Flux<TopicPersistence> get() {
        return userContext.getUserId().flatMapMany(userId -> topicService.getAll(userId));
    }

    @PutMapping("/{topicId}/subscription")
    public Mono<Object> subscribe(@PathVariable Integer topicId) {
        return userContext.getUserId().flatMap(userId -> 
            topicService.subscribe(topicId, userId)
        );
    }

    @DeleteMapping("/{topicId}/subscription")
    public Mono<Object> unsubscribe(@PathVariable Integer topicId) {
        return userContext.getUserId().flatMap(userId -> 
            topicService.unsubscribe(topicId, userId)
        );
    }
}
