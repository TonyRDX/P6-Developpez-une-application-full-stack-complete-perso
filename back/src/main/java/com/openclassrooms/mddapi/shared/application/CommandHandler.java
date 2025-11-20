package com.openclassrooms.mddapi.shared.application;

public interface CommandHandler<T> {
    public void handle(T message);
}
