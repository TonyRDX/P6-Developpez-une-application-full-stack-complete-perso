package com.openclassrooms.mddapi.infrastructure.featuregroup.post.handler;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.application.usecase.createpost.CreatePostCommand;
import com.openclassrooms.mddapi.application.usecase.createpost.CreatePostHandler;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostPublisher;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.mapper.PostMapper;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.unitofwork.CreatePostCommandUoW;
import com.openclassrooms.mddapi.infrastructure.persistence.entity.PostPersistence;
import com.openclassrooms.mddapi.infrastructure.persistence.repository.PostRepository;
import com.openclassrooms.mddapi.infrastructure.persistence.repository.TopicRepository;
import com.openclassrooms.mddapi.infrastructure.persistence.repository.UserRepository;
import com.openclassrooms.mddapi.shared.infrastructure.MessageHandler;

import reactor.core.publisher.Mono;

@Component
public class CreatePostSetupHandler implements MessageHandler<CreatePostCommand, PostPersistence> {
    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final PostPublisher postPublisher;
    private final PostMapper postMapper;
    
    public CreatePostSetupHandler(
        PostRepository postRepository,
        TopicRepository topicRepository,
        UserRepository userRepository,
        PostPublisher postPublisher,
        PostMapper postMapper
    ) {
        this.postRepository = postRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.postPublisher = postPublisher;
        this.postMapper = postMapper;
    }

    @Override
    public Mono<PostPersistence> handle(CreatePostCommand message) {
        CreatePostCommandUoW uow = new CreatePostCommandUoW(
            this.postRepository, 
            this.topicRepository, 
            this.userRepository, 
            this.postPublisher, 
            this.postMapper);
        CreatePostHandler handler = new CreatePostHandler(uow);
        handler.handle(message);
        return uow.getProvider(message);
    }
}
