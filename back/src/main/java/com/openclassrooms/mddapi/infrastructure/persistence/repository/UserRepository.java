package com.openclassrooms.mddapi.infrastructure.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.infrastructure.persistence.entity.User;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
    Mono<User> findByEmail(String email);
    Mono<User> findByName(String name);
}
