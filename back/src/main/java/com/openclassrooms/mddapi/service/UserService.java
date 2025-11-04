package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    private static final Map<Integer, List<Integer>> topicsByUser = Map.of(
            1, List.of(1, 2),
            2, List.of(3)
    );

    public Mono<List<Integer>> getTopics(Mono<Integer> userId) {
        return userId
            .flatMap(id -> Mono.justOrEmpty(topicsByUser.get(id)))
            .defaultIfEmpty(List.of());
    }
}
