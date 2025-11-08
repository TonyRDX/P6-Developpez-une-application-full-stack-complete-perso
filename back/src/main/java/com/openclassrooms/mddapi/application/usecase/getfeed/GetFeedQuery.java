package com.openclassrooms.mddapi.application.usecase.getfeed;

import reactor.core.publisher.Mono;

// TODO clear mono

public record GetFeedQuery
 (
    Mono<Integer> userId,
    Boolean recentFirst
) {}
