package com.openclassrooms.mddapi.core.infrastructure.featuregroup.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.core.application.service.TopicService;
import com.openclassrooms.mddapi.core.application.usecase.createpost.CreatePostCommand;
import com.openclassrooms.mddapi.core.application.usecase.getpost.GetPostByIdQuery;
import com.openclassrooms.mddapi.core.infrastructure.dto.AddPostRequest;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.PostPersistence;
import com.openclassrooms.mddapi.core.infrastructure.service.ReactiveUserContext;
import com.openclassrooms.mddapi.shared.infrastructure.MessageHandler;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200") 
public class PostController {
    private final MessageHandler<GetPostByIdQuery, PostPersistence> postQueryHandler;
    private final MessageHandler<CreatePostCommand, PostPersistence> createPostHandler;

    public PostController(
        TopicService topicService,
        MessageHandler<GetPostByIdQuery, PostPersistence> postQueryHandler,
        MessageHandler<CreatePostCommand, PostPersistence> createPostHandler
    ) {
        this.postQueryHandler = postQueryHandler;
        this.createPostHandler = createPostHandler;
    }

    @Autowired
    private ReactiveUserContext userContext;

    @PostMapping
    public Mono<PostPersistence> post(@RequestBody AddPostRequest request) {
        Mono<Integer> userIdMono = userContext.getUserId();

        return userIdMono.flatMap(userId -> {
                CreatePostCommand cmd = new CreatePostCommand(
                    request.title(), 
                    request.content(), 
                    request.topic_id(),
                    userId
                );
                return this.createPostHandler.handle(cmd);
            });
    }

    @GetMapping("/{id}")
    public Mono<PostPersistence> get(@PathVariable Integer id) {
        return postQueryHandler.handle(new GetPostByIdQuery(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Failed to fetch")));
    }
}
