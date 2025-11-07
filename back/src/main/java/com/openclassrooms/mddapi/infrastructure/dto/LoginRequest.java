package com.openclassrooms.mddapi.infrastructure.dto;

public record LoginRequest (
    String email,
    String name,
    String password
) {}
