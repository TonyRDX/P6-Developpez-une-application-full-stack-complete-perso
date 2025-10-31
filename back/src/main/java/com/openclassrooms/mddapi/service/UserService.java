package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final Map<String, List<String>> themesByUser = Map.of(
            "u1", List.of("java", "spring"),
            "u2", List.of("kotlin")
    );

    public Mono<List<String>> getThemes(String userId) {
        return Mono.justOrEmpty(themesByUser.get(userId))
                   .defaultIfEmpty(List.of());
    }
}
