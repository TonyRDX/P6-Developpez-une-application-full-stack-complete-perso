package com.openclassrooms.mddapi.application.unitofwork;

import org.reactivestreams.Publisher;

public interface BasicUnitOfWork<T> {
    void register(T entity);
    void load(Class<T> type, Integer id);
    Publisher<T> completeAndReturn();
}
