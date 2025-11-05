package com.openclassrooms.mddapi.infrastructure.sse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Sinks;

@Configuration
public class SseConfig {
    @Bean
    public Sinks.Many<PostSse> postSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }
}
