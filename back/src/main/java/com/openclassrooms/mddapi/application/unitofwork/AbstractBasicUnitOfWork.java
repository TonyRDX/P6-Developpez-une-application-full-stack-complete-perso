package com.openclassrooms.mddapi.application.unitofwork;

import org.reactivestreams.Publisher;

public abstract class AbstractBasicUnitOfWork<T> implements BasicUnitOfWork<T> {
    public void register(T entity) {
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }
    public void load(Class<T> type, Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }
    public Publisher<T> completeAndReturn() {
        throw new UnsupportedOperationException("Unimplemented method 'completeAndReturn'");
    }
}