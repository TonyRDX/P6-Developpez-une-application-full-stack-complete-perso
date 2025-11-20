package com.openclassrooms.mddapi.core.infrastructure.featuregroup.post.handler;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.core.application.usecase.getpost.GetPostByIdQuery;
import com.openclassrooms.mddapi.core.application.usecase.getpost.GetPostByIdQueryHandler;
import com.openclassrooms.mddapi.core.infrastructure.featuregroup.post.unitofwork.PostUnitOfWork;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.PostPersistence;
import com.openclassrooms.mddapi.core.infrastructure.persistence.repository.PostRepository;
import com.openclassrooms.mddapi.shared.application.unitofwork.BasicUnitOfWork;
import com.openclassrooms.mddapi.shared.infrastructure.MessageHandler;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class GetByIdHandler implements MessageHandler<GetPostByIdQuery, Mono<PostPersistence>>{
    private final PostRepository postRepository;

    public GetByIdHandler(
        PostRepository postRepository
    ) {
        this.postRepository = postRepository;
    }

    @Override
    public Mono<PostPersistence> handle(GetPostByIdQuery message) {
        return this.postRepository.findById(message.id())
            .flatMap(post -> {
                HashMap<Integer, PostPersistence> map = new HashMap<Integer, PostPersistence>();
                map.put(message.id(), post);
                PostUnitOfWork.UoWContextImpl ctx = new PostUnitOfWork.UoWContextImpl(map);
                PostUnitOfWork uow = new PostUnitOfWork();
                uow.loadContext(ctx);
                return Mono.just((BasicUnitOfWork<PostPersistence>) uow);
            })
            .flatMap( uow ->
                Mono.fromCallable(() -> { 
                    GetPostByIdQueryHandler handler = new GetPostByIdQueryHandler(uow);
                    return (PostPersistence) handler.handle(message).entity();
                }).publishOn(Schedulers.boundedElastic())
            );
    }
}
