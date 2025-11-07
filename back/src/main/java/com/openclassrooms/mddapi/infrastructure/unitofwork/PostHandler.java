package com.openclassrooms.mddapi.infrastructure.unitofwork;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.shared.infrastructure.MessageHandler;

import reactor.core.publisher.Mono;

// @Component
// public class PostHandler implements MessageHandler<Post> {

//     @Override
//     public Mono<?> handle(Post message) {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'handle'");
//     }
//     // private final PostRepository postRepository;
//     // private final PostPublisher postPublisher;

//     // public PostHandler(
//     //     PostRepository postRepository,
//     //     PostPublisher postPublisher
//     // ) {
//     //     this.postRepository = postRepository;
//     //     this.postPublisher = postPublisher;
//     // }

//     // @Override
//     // public Mono<BasicUnitOfWorkPojo> handle(Integer id) {
//     //     return this.postRepository.findById(id)
//     //         .flatMap(post -> {
//     //             HashMap<Integer, Post> map = new HashMap<Integer, Post>();
//     //             map.put(id, post);
//     //             BasicUnitOfWorkPojo.UoWContextImpl ctx = new BasicUnitOfWorkPojo.UoWContextImpl(map);
//     //             BasicUnitOfWorkPojo uow = new BasicUnitOfWorkPojo();
//     //             uow.loadContext(ctx);
//     //             return Mono.just((BasicUnitOfWorkPojo)uow);
//     //         });
//     // }
// }

 

