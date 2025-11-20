package com.openclassrooms.mddapi.core.application.usecase.createpost;

import java.time.Instant;

import com.openclassrooms.mddapi.core.domain.model.Post;
import com.openclassrooms.mddapi.shared.application.CommandHandler;
import com.openclassrooms.mddapi.shared.application.unitofwork.UseCaseUnitOfWork;

public class CreatePostHandler implements CommandHandler<CreatePostCommand> {
    private final UseCaseUnitOfWork<CreatePostCommand> uow;

    public CreatePostHandler(
        UseCaseUnitOfWork<CreatePostCommand> uow
    ) {
        this.uow = uow;
    }

    @Override
    public void handle(CreatePostCommand command) {
        // consider infrastructure manage entity relational for now

        Post post = new Post();
        post.setTitle(command.title());
        post.setContent(command.content());
        post.setCreatedAt(Instant.now());

        uow.<Post>register(post);
        uow.complete();
    }
}
