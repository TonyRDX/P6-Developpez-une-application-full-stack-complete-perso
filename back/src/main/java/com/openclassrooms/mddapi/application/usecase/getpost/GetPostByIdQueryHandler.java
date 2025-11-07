package com.openclassrooms.mddapi.application.usecase.getpost;

import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.shared.application.QueryHandler;
import com.openclassrooms.mddapi.shared.application.response.BasicUseCaseResponse;
import com.openclassrooms.mddapi.shared.application.unitofwork.BasicUnitOfWork;

public class GetPostByIdQueryHandler implements QueryHandler<GetPostByIdQuery> {
    private final BasicUnitOfWork<Post> uow;

    public GetPostByIdQueryHandler (BasicUnitOfWork<Post> uow) {
        this.uow = uow;
    }

    public BasicUseCaseResponse handle(GetPostByIdQuery query) {
        return new BasicUseCaseResponse(uow.load(Post.class, query.id()));
    }
}
