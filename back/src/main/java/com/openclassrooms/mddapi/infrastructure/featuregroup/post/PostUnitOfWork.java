package com.openclassrooms.mddapi.infrastructure.featuregroup.post;

import java.util.Map;

import org.reactivestreams.Publisher;

import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.shared.application.unitofwork.BasicUnitOfWork;
import com.openclassrooms.mddapi.shared.application.unitofwork.UoWContext;

import reactor.core.publisher.Mono;

public class PostUnitOfWork implements BasicUnitOfWork<Post> {
    private UoWContextImpl ctx;

    public Post load(Class type, Integer id) {
        return ctx.postRepo().get(id);
    }

    public Publisher<Post> completeAndReturn() {
        return Mono.just(new Post());
    }

    public void loadContext(UoWContext ctx) {
        if (ctx instanceof UoWContextImpl c) {
            this.ctx = c;
        }
    }

    public record UoWContextImpl(Map<Integer, Post> postRepo) implements UoWContext {}

    @Override
    public void register(Post entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }
}
