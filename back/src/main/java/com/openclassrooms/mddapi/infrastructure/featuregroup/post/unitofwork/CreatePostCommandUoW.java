package com.openclassrooms.mddapi.infrastructure.featuregroup.post.unitofwork;

import com.openclassrooms.mddapi.application.usecase.createpost.CreatePostCommand;
import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostPublisher;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostSse;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.mapper.PostMapper;
import com.openclassrooms.mddapi.infrastructure.persistence.entity.PostPersistence;
import com.openclassrooms.mddapi.infrastructure.persistence.repository.PostRepository;
import com.openclassrooms.mddapi.infrastructure.persistence.repository.TopicRepository;
import com.openclassrooms.mddapi.infrastructure.persistence.repository.UserRepository;
import com.openclassrooms.mddapi.shared.application.response.UseCaseResponse;
import com.openclassrooms.mddapi.shared.application.unitofwork.UseCaseUnitOfWork;

import reactor.core.publisher.Mono;

public class CreatePostCommandUoW implements UseCaseUnitOfWork<CreatePostCommand> {
    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final PostPublisher postPublisher;
    private final PostMapper postMapper;

    private Post postToSave;
    private boolean completed = false;

    public CreatePostCommandUoW(
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

    public void register(Post entity) {
        this.postToSave = entity; 
    }
    
    public Mono<PostPersistence> getProvider(CreatePostCommand cmd) {
        if (!this.completed)
            throw new IllegalStateException("Transaction not completed from the handler");

        PostPersistence postDb = this.postMapper.toPersistence(this.postToSave);
        postDb.setTopicId(cmd.topic_id());
        postDb.setAuthorId(cmd.user_id());
        
        Mono<Boolean> topicExists = topicRepository.existsById(cmd.topic_id());
        Mono<Boolean> userExists = userRepository.existsById(cmd.user_id());
        Mono<PostPersistence> persisted = Mono.defer(() -> this.getSaveProvider(postDb));

        return  Mono.zip(topicExists, userExists)
            .flatMap(tuple -> {
                boolean topicOk = tuple.getT1();
                boolean userOk = tuple.getT2(); 

                if (!topicOk) {
                    return Mono.error(new RuntimeException("Topic not found"));
                }
                if (!userOk) {
                    return Mono.error(new RuntimeException("User not found"));
                }
                
                return persisted;
            }
        );
    }

    private Mono<PostPersistence> getSaveProvider(PostPersistence postDb) {
        return this.postRepository.save(postDb)
            .doOnSuccess(saved -> {
                this.postPublisher.publish(
                    new PostSse(
                        saved.getId(),
                        saved.getTopicId(),
                        saved.getAuthorId()
                    )
                );
            });
    }
    
    @Override
    public void complete() throws UnsupportedOperationException {
        this.completed = true;
    }
    
    @Override
    public <T> void register(T entity) {
        if (entity instanceof Post post) {   
            register(post);  
            return;
        }
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public <T> T load(Class<T> type, Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }

    @Override
    public UseCaseResponse completeAndReturn() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Unimplemented method 'completeAndReturn'");
    }
}
