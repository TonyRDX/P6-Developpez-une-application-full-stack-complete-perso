package com.openclassrooms.mddapi.core.infrastructure.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.Comment;

import reactor.core.publisher.Flux;

@Repository
public interface CommentRepository extends ReactiveCrudRepository<Comment, Integer> {
    Flux<Comment> findAllByPostId(Integer postId);
}
