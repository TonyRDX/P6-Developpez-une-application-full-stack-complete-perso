package com.openclassrooms.mddapi.infrastructure.featuregroup.post;

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
import com.openclassrooms.mddapi.application.usecase.createpost.CreatePostCommand;
import com.openclassrooms.mddapi.application.usecase.getpost.GetPostByIdQuery;
import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.domain.model.Topic;
import com.openclassrooms.mddapi.infrastructure.dto.AddPostRequest;
import com.openclassrooms.mddapi.infrastructure.service.ReactiveUserContext;
import com.openclassrooms.mddapi.shared.infrastructure.MessageHandler;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200") 
public class PostController {

    private final PostService postService;
    private final TopicService topicService;
    private final MessageHandler<GetPostByIdQuery, Post> postQueryHandler;

    public PostController(
        PostService postService,    
        TopicService topicService,
        MessageHandler<GetPostByIdQuery, Post> postQueryHandler
    ) {
        this.postService = postService;
        this.postQueryHandler = postQueryHandler;
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

                CreatePostCommand cmd = new CreatePostCommand(
                    request.title(), 
                    request.content(), 
                    request.topic_id(),
                    userId
                );
                
                return Mono.from((Mono<Post>) postService.create(cmd));
            });
    }

    @GetMapping("/{id}")
    public Mono<Post> get(@PathVariable Integer id) {
        return postQueryHandler.handle(new GetPostByIdQuery(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Failed to fetch")));
    }
}
