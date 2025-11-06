package com.openclassrooms.mddapi.infrastructure.unitofwork;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.application.unitofwork.AbstractBasicUnitOfWork;
import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.infrastructure.repository.PostRepository;
import com.openclassrooms.mddapi.infrastructure.sse.PostPublisher;
import com.openclassrooms.mddapi.infrastructure.sse.PostSse;

@Component
public class BasicUnitOfWorkImpl extends AbstractBasicUnitOfWork<Post> {
    @Autowired private PostRepository postRepository;
    @Autowired private PostPublisher postPublisher;

    private Post entity;
    public void register(Post e) { 
        this.entity = e; 
    }

    public Publisher<Post> completeAndReturn() {
        return this.postRepository.save(entity)
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


