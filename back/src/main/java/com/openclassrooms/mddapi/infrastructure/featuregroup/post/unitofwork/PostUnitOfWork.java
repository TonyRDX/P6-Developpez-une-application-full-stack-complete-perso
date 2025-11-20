package com.openclassrooms.mddapi.infrastructure.featuregroup.post.unitofwork;

import java.util.Map;

import org.reactivestreams.Publisher;

import com.openclassrooms.mddapi.infrastructure.persistence.entity.PostPersistence;
import com.openclassrooms.mddapi.shared.application.unitofwork.BasicUnitOfWork;
import com.openclassrooms.mddapi.shared.application.unitofwork.UoWContext;

import reactor.core.publisher.Mono;

public class PostUnitOfWork implements BasicUnitOfWork<PostPersistence> {
    private UoWContextImpl ctx;

    public PostPersistence load(Class type, Integer id) {
        return ctx.postRepo().get(id);
    }

    public Publisher<PostPersistence> completeAndReturn() {
        return Mono.just(new PostPersistence());
    }

    public void loadContext(UoWContext ctx) {
        if (ctx instanceof UoWContextImpl c) {
            this.ctx = c;
        }
    }

    public record UoWContextImpl(Map<Integer, PostPersistence> postRepo) implements UoWContext {}

    @Override
    public void register(PostPersistence entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }
}
