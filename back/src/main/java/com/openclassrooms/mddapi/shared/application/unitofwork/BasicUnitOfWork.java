package com.openclassrooms.mddapi.shared.application.unitofwork;

import org.reactivestreams.Publisher;


public interface BasicUnitOfWork<T> {
    void register(T entity);
    T load(Class<T> type, Integer id);
    Publisher<T> completeAndReturn();
}
