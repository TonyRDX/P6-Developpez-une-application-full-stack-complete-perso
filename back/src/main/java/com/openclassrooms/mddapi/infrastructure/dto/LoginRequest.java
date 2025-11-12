package com.openclassrooms.mddapi.infrastructure.dto;

public record LoginRequest (
    String identifier,
    String password
) {}
