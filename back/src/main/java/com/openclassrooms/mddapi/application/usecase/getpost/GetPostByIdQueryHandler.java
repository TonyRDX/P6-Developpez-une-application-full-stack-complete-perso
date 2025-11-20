package com.openclassrooms.mddapi.application.usecase.getpost;

import com.openclassrooms.mddapi.infrastructure.persistence.entity.PostPersistence;
import com.openclassrooms.mddapi.shared.application.QueryHandler;
import com.openclassrooms.mddapi.shared.application.response.BasicUseCaseResponse;
import com.openclassrooms.mddapi.shared.application.unitofwork.BasicUnitOfWork;

public class GetPostByIdQueryHandler implements QueryHandler<GetPostByIdQuery> {
    private final BasicUnitOfWork<PostPersistence> uow;

    public GetPostByIdQueryHandler (BasicUnitOfWork<PostPersistence> uow) {
        this.uow = uow;
    }

    public BasicUseCaseResponse handle(GetPostByIdQuery query) {
        return new BasicUseCaseResponse(uow.load(PostPersistence.class, query.id()));
    }
}
