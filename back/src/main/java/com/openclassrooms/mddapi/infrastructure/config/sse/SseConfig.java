package com.openclassrooms.mddapi.infrastructure.config.sse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.openclassrooms.mddapi.infrastructure.featuregroup.post.PostSse;

import reactor.core.publisher.Sinks;

@Configuration
public class SseConfig {
    @Bean
    public Sinks.Many<PostSse> postSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }
}
