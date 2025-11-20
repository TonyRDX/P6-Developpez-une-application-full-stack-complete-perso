package com.openclassrooms.mddapi.infrastructure.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.infrastructure.dto.MePatch;
import com.openclassrooms.mddapi.infrastructure.dto.UserResponse;
import com.openclassrooms.mddapi.infrastructure.persistence.entity.User;
import com.openclassrooms.mddapi.infrastructure.persistence.repository.UserRepository;
import com.openclassrooms.mddapi.infrastructure.service.ReactiveUserContext;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/me")
@CrossOrigin(origins = "http://localhost:4200") 
public class MeController {
    private final UserRepository userRepository;
    private final ReactiveUserContext userContext;
    private final PasswordEncoder passwordEncoder;
    
    public MeController(
            UserRepository userRepository,
            ReactiveUserContext userContext,
            PasswordEncoder passwordEncoder
        ) {
        this.userRepository = userRepository;
        this.userContext = userContext;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public Mono<UserResponse> subscribeUser() {
        return userRepository.findById(userContext.getUserId())
            .map(this::userToResponse);
    }

    @PatchMapping
    public Mono<UserResponse> updateMe(@RequestBody MePatch request) {
        return userRepository.findById(userContext.getUserId())
            .map(user -> {
                return this.patch(request, user);
            })
            .flatMap(this.userRepository::save)
            .map(this::userToResponse);
    }

    private UserResponse userToResponse(User user) {
        return new UserResponse(
            user.getId(), 
            user.getEmail(), 
            user.getName()
        );
    }

    private User patch(MePatch patch, User user) {
        user.setEmail(patch.email());
        user.setName(patch.name());
        if (patch.password() != null && !"".equals(patch.password())) {
            user.setPassword(passwordEncoder.encode(patch.password()));
        }
        return user;
    }
}
