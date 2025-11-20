package com.openclassrooms.mddapi.core.infrastructure.dto;

public record LoginRequest (
    String identifier,
    String password
) {}
