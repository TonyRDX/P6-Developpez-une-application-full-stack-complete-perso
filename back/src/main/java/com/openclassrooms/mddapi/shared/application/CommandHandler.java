package com.openclassrooms.mddapi.shared.application;

import org.reactivestreams.Publisher;

public interface CommandHandler<T> {
    public Publisher<?> handle(T message);
}
