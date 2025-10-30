package com.openclassrooms.mddapi.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.openclassrooms.mddapi.model.Post;

@Repository
public interface PostRepository extends ReactiveCrudRepository<Post, Integer> {
}
