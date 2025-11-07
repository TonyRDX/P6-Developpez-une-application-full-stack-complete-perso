package com.openclassrooms.mddapi.shared.infrastructure;

import reactor.core.publisher.Mono;

public interface MessageHandler<T, U> {
    Mono<U> handle(T message);
}
