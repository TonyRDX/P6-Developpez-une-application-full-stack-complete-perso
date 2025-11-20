package com.openclassrooms.mddapi.core.infrastructure.controller;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.core.infrastructure.dto.AuthResponse;
import com.openclassrooms.mddapi.core.infrastructure.dto.LoginRequest;
import com.openclassrooms.mddapi.core.infrastructure.dto.SubsribeUserRequest;
import com.openclassrooms.mddapi.core.infrastructure.dto.UserResponse;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.User;
import com.openclassrooms.mddapi.core.infrastructure.persistence.repository.UserRepository;
import com.openclassrooms.mddapi.shared.infrastructure.service.JwtService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200") 
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
        ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private static final String PASSWORD_REGEX =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?.*(){}\\[\\]\\-_/])(?=\\S+$).{8,}$";

    public boolean isValidPassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    @PostMapping("/register")
    public Mono<UserResponse> subscribeUser(@RequestBody SubsribeUserRequest request) {
        if (!isValidPassword(request.password())) {
            return Mono.error(new IllegalArgumentException(
                    "Le mot de passe doit contenir au moins 8 caractères, " +
                    "une majuscule, une minuscule, un chiffre et un caractère spécial."
            ));
        }
        
        User newUser = new User();
        newUser.setEmail(request.email());
        newUser.setName(request.name());
        newUser.setPassword(passwordEncoder.encode(request.password()));

        return userRepository.save(newUser)
            .map(this::userToResponse);
    }

    @PostMapping("/login")
    public Mono<AuthResponse> loginUser(@RequestBody LoginRequest request) {
        Mono<User> userMono = userRepository.findByEmail(request.identifier())
            .switchIfEmpty(userRepository.findByName(request.identifier()))
            .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid credentials")));

        return userMono
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
