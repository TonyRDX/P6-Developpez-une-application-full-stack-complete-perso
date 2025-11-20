package com.openclassrooms.mddapi.core.infrastructure.controller;

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

import com.openclassrooms.mddapi.core.application.service.TopicService;
import com.openclassrooms.mddapi.core.infrastructure.dto.AddTopicRequest;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.Topic;
import com.openclassrooms.mddapi.core.infrastructure.service.ReactiveUserContext;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "http://localhost:4200") 
public class TopicController {

    private final TopicService topicService;
    @Autowired
    private ReactiveUserContext userContext;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public Mono<Topic> post(@RequestBody AddTopicRequest request) {
        Topic topic = new Topic();
        topic.setTitle(request.title());
        topic.setContent(request.content());

        return topicService.create(topic);
    }

    @GetMapping
    public Flux<Topic> get() {
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
