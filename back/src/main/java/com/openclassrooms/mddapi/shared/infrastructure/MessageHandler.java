package com.openclassrooms.mddapi.shared.infrastructure;

public interface MessageHandler<T, U> {
    U handle(T message);
}
