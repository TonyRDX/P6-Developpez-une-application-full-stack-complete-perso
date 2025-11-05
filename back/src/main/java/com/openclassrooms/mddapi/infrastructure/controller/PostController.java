package com.openclassrooms.mddapi.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.application.service.PostService;
import com.openclassrooms.mddapi.application.service.TopicService;
import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.domain.model.Topic;
import com.openclassrooms.mddapi.infrastructure.dto.AddPostRequest;
import com.openclassrooms.mddapi.infrastructure.service.ReactiveUserContext;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200") 
public class PostController {

    private final PostService postService;
    private final TopicService topicService;

    public PostController(
        PostService postService,    
        TopicService topicService    
    ) {
        this.postService = postService;
        this.topicService = topicService;
    }

    @Autowired
    private ReactiveUserContext userContext;

    @PostMapping
    public Mono<Post> post(@RequestBody AddPostRequest request) {

        Mono<Integer> userIdMono = userContext.getUserId();
        Mono<Topic> topicMono = topicService.getOne(request.topic_id())
            .switchIfEmpty(Mono.error(new RuntimeException("Topic not found")));

        return userIdMono.zipWith(topicMono) // combiner les flux
            .flatMap(tuple -> {              // executer asynchrone
                Integer userId = tuple.getT1();
                Topic topic = tuple.getT2();

                Post post = new Post();
                post.setTitle(request.title());
                post.setContent(request.content());
                post.setTopicId(topic.getId());
                post.setAuthorId(userId);

                return postService.create(post);
            });
    }

    @GetMapping("/{id}")
    public Mono<Post> get(@PathVariable Integer id) {
        return postService.getOne(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Failed to fetch")));
    }
}
