package com.openclassrooms.mddapi.shared.application.unitofwork;

import org.reactivestreams.Publisher;

/**
 * A basic Unit of Work is useful to implements reusable transaction 
 * or pre-fetch useful data to maintains sync business rules.
 */
public interface BasicUnitOfWork<T> {
    void register(T entity);
    T load(Class<T> type, Integer id);
    Publisher<T> completeAndReturn();
}
