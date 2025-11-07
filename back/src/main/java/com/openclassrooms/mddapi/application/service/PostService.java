package com.openclassrooms.mddapi.application.service;

import java.time.Instant;
import java.util.List;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.application.usecase.createpost.CreatePostCommand;
import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.infrastructure.repository.PostRepository;
import com.openclassrooms.mddapi.shared.application.unitofwork.BasicUnitOfWork;
import com.openclassrooms.mddapi.shared.application.unitofwork.UseCaseUnitOfWork;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UseCaseUnitOfWork<CreatePostCommand> uow;
    private final ObjectProvider<BasicUnitOfWork<Post>> uowProvider;

    public PostService(
        PostRepository postRepository,
        UseCaseUnitOfWork<CreatePostCommand> uow,
        ObjectProvider<BasicUnitOfWork<Post>> uowProvider
    ) {
        this.postRepository = postRepository;
        this.uow = uow;
        this.uowProvider = uowProvider;
    }

    public Flux<Post> getRecent(Mono<List<Integer>> topicIds) {
        return topicIds.flatMapMany(ids ->
            ids.isEmpty() ?
                Flux.empty() : 
                postRepository.findAllByTopicIdIn(ids)
        );
    }

    public Publisher<?> create(CreatePostCommand command) {
        // TODO check topic and user id

        Post post = new Post();
        post.setTitle(command.title());
        post.setContent(command.content());
        post.setTopicId(command.topic_id());
        post.setAuthorId(command.user_id());
        post.setCreatedAt(Instant.now());

        uow.<Post>register(post);
        return uow.completeAndReturn();
    }
}
