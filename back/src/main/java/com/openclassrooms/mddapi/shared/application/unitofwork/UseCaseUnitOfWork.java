package com.openclassrooms.mddapi.shared.application.unitofwork;

import org.reactivestreams.Publisher;

/**
 * A Unit of Work dedicated to a specific application use case.
 * <p>
 * This interface is intended for advanced or specialized Unit of Work implementations.
 * When possible, prefer using {@link BasicUnitOfWork} for general-purpose scenarios.
 * </p>
 *
 * @param <UC> the use case identifier type, used to load the proper Unit of Work
 *             (e.g. {@code CreatePostCommand})
 */
public interface UseCaseUnitOfWork<UC> {
    <T> void register(T entity) throws UnsupportedOperationException;
    <T> T load(Class<T> type, Integer id) throws UnsupportedOperationException;
    Publisher<?> completeAndReturn() throws UnsupportedOperationException;
}
