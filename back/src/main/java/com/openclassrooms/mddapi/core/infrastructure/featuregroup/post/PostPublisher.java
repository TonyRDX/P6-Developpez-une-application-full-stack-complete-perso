package com.openclassrooms.mddapi.core.infrastructure.featuregroup.post;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

@Component
public class PostPublisher {

    private final Sinks.Many<PostSse> sink = Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);

    public void publish(PostSse post) {
        Sinks.EmitResult result = sink.tryEmitNext(post);
        if (result.isFailure()) {
            System.out.println("[sink] emit failed: " + result);
        }
    }

    public Flux<PostSse> flux() {
        return sink.asFlux().doOnSubscribe(s -> System.out.println("[sink] subscriber"))
        .doOnComplete(() -> System.out.println("[sink] completed"))
        .doOnCancel(() -> System.out.println("[sink] canceled"))
        .doOnError(e -> System.out.println("[sink] error " + e.getMessage()));
    }
}
