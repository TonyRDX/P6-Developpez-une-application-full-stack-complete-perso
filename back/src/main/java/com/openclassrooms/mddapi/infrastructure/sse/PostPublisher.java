package com.openclassrooms.mddapi.infrastructure.sse;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class PostPublisher {

    private final Sinks.Many<PostSse> sink = Sinks.many().multicast().onBackpressureBuffer();

    public void publish(PostSse post) {
        sink.tryEmitNext(post);
    }

    public Flux<PostSse> flux() {
        return sink.asFlux();
    }
}
