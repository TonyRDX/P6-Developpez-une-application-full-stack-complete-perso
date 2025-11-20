package com.openclassrooms.mddapi.shared.infrastructure;

import reactor.core.publisher.Mono;

public interface MessageHandler<T, U> {
    U handle(T message);
}
