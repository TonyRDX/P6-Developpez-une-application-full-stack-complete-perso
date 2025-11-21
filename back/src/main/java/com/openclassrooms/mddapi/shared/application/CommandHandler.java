package com.openclassrooms.mddapi.shared.application;

import com.openclassrooms.mddapi.shared.application.unitofwork.BasicUnitOfWork;
import com.openclassrooms.mddapi.shared.application.unitofwork.UseCaseUnitOfWork;

/**
 * Synchronous executions of business rules.
 * Often uses a {@link BasicUnitOfWork} or {@link UseCaseUnitOfWork}
 */
public interface CommandHandler<T> {
    public void handle(T message);
}
