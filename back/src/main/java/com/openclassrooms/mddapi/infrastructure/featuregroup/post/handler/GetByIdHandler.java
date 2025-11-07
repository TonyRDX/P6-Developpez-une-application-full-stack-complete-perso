package com.openclassrooms.mddapi.infrastructure.featuregroup.post.handler;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.application.usecase.getpost.GetPostByIdQuery;
import com.openclassrooms.mddapi.application.usecase.getpost.GetPostByIdQueryHandler;
import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostPublisher;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostUnitOfWork;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostUnitOfWork.UoWContextImpl;
import com.openclassrooms.mddapi.infrastructure.repository.PostRepository;
import com.openclassrooms.mddapi.shared.application.unitofwork.BasicUnitOfWork;
import com.openclassrooms.mddapi.shared.infrastructure.MessageHandler;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class GetByIdHandler implements MessageHandler<GetPostByIdQuery, Post>{
    private final PostRepository postRepository;
    private final PostPublisher postPublisher;

    public GetByIdHandler(
        PostRepository postRepository,
        PostPublisher postPublisher
    ) {
        this.postRepository = postRepository;
        this.postPublisher = postPublisher;
    }

    @Override
    public Mono<Post> handle(GetPostByIdQuery message) {
        return this.postRepository.findById(message.id())
            .flatMap(post -> {
                HashMap<Integer, Post> map = new HashMap<Integer, Post>();
                map.put(message.id(), post);
                PostUnitOfWork.UoWContextImpl ctx = new PostUnitOfWork.UoWContextImpl(map);
                PostUnitOfWork uow = new PostUnitOfWork();
                uow.loadContext(ctx);
                return Mono.just((BasicUnitOfWork<Post>) uow);
            })
            .flatMap( uow ->
                Mono.fromCallable(() -> { 
                    GetPostByIdQueryHandler handler = new GetPostByIdQueryHandler(uow);
                    return (Post) handler.handle(message).entity();
                }).publishOn(Schedulers.boundedElastic())
            );
    }
}
