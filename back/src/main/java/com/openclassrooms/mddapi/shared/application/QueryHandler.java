package com.openclassrooms.mddapi.shared.application;

import com.openclassrooms.mddapi.shared.application.response.UseCaseResponse;

public interface QueryHandler<T> {
    public UseCaseResponse handle(T message);
}
