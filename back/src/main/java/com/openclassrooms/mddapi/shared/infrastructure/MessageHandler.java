package com.openclassrooms.mddapi.shared.infrastructure;

/**
 * Injected by the controller, implemented next to it in the slice.
 *
 * @param <T> message type (query or command)
 * @param <U> return type (often Flux or Mono with Webflux)
 */
public interface MessageHandler<T, U> {
    U handle(T message);
}
