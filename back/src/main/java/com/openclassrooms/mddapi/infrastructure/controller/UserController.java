package com.openclassrooms.mddapi.infrastructure.controller;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.domain.model.User;
import com.openclassrooms.mddapi.infrastructure.dto.LoginRequest;
import com.openclassrooms.mddapi.infrastructure.dto.SubsribeUserRequest;
import com.openclassrooms.mddapi.infrastructure.dto.AuthResponse;
import com.openclassrooms.mddapi.infrastructure.dto.UserResponse;
import com.openclassrooms.mddapi.infrastructure.repository.UserRepository;
import com.openclassrooms.mddapi.infrastructure.service.JwtService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200") 
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    public UserController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
        ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/subscribe")
    public Mono<UserResponse> subscribeUser(@RequestBody SubsribeUserRequest request) {
        User newUser = new User();
        newUser.setEmail(request.email());
        newUser.setName(request.name());
        newUser.setPassword(passwordEncoder.encode(request.password()));

        return userRepository.save(newUser)
            .map(this::userToResponse);
    }

    @GetMapping("/login")
    public Mono<AuthResponse> loginUser(@RequestBody LoginRequest request) {
        return userRepository.findByEmail(request.email())
            .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid credentials")))
            .filter(user -> passwordEncoder.matches(request.password(), user.getPassword()))
            .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid credentials")))
            .map(user -> new AuthResponse(jwtService.generateToken(user)));
    }

    private UserResponse userToResponse(User user) {
        return new UserResponse(
            user.getId(), 
            user.getEmail(), 
            user.getName()
        );
    }
}
