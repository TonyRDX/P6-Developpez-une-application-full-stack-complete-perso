package com.openclassrooms.mddapi.service;

// import org.springframework.security.core.context.ReactiveSecurityContextHolder;
// import org.springframework.security.core.context.SecurityContext;
// import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class ReactiveUserContext {

    public Mono<Integer> getUserId() {
        return Mono.just(1);
        // return ReactiveSecurityContextHolder.getContext()
        //     .map(SecurityContext::getAuthentication)
        //     .filter(JwtAuthenticationToken.class::isInstance)
        //     .cast(JwtAuthenticationToken.class)
        //     .map(auth -> Integer.parseInt(auth.getToken().getSubject()));
    }
}
