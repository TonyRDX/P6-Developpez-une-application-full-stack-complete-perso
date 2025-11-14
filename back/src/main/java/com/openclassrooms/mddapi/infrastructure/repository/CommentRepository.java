package com.openclassrooms.mddapi.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.infrastructure.persistence.Comment;

import reactor.core.publisher.Flux;

@Repository
public interface CommentRepository extends ReactiveCrudRepository<Comment, Integer> {
    Flux<Comment> findAllByPostId(Integer postId);
}
