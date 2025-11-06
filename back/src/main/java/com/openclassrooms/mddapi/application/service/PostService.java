package com.openclassrooms.mddapi.application.service;

import java.time.Instant;
import java.util.List;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.application.CreatePostCommand;
import com.openclassrooms.mddapi.application.unitofwork.UseCaseUnitOfWork;
import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.infrastructure.repository.PostRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UseCaseUnitOfWork<CreatePostCommand> uow;

    public PostService(
        PostRepository postRepository,
        UseCaseUnitOfWork<CreatePostCommand> uow
    ) {
        this.postRepository = postRepository;
        this.uow = uow;
    }

    public Flux<Post> getRecent(Mono<List<Integer>> topicIds) {
        return topicIds.flatMapMany(ids ->
            ids.isEmpty() ?
                Flux.empty() : 
                postRepository.findAllByTopicIdIn(ids)
        );
    }

    public Mono<Post> getOne(Integer id) {
        return postRepository.findById(id);
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
