package com.openclassrooms.mddapi.infrastructure.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.application.service.TopicService;
import com.openclassrooms.mddapi.domain.model.Topic;
import com.openclassrooms.mddapi.infrastructure.dto.AddTopicRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "http://localhost:4200") 
public class TopicController {

    private final TopicService topicService;

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
        return topicService.getAll();
    }
}
