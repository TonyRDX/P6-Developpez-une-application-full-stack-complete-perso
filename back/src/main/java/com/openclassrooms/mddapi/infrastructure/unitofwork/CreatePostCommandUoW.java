package com.openclassrooms.mddapi.infrastructure.unitofwork;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.application.usecase.createpost.CreatePostCommand;
import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostPublisher;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostSse;
import com.openclassrooms.mddapi.infrastructure.repository.PostRepository;
import com.openclassrooms.mddapi.shared.application.unitofwork.UseCaseUnitOfWork;

@Component
public class CreatePostCommandUoW implements UseCaseUnitOfWork<CreatePostCommand>{
    private final PostRepository postRepository;
    @Autowired private PostPublisher postPublisher;
    private Post postToSave;

    public CreatePostCommandUoW(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public <T> void register(T entity) {
        if (entity instanceof Post post) {   
            register(post);  
            return;
        }
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    public void register(Post entity) {
        this.postToSave = entity; 
    }

    @Override
    public <T> T load(Class<T> type, Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }

    @Override
    public Publisher<Post> completeAndReturn() {
        return this.postRepository.save(postToSave)
            .doOnSuccess(saved -> {
                    this.postPublisher.publish(
                        new PostSse(
                            saved.getId(), 
                            saved.getTitle(), 
                            saved.getContent(), 
                            saved.getTopicId()
                        )
                    );
                });
    }
    
}
