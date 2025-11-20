package com.openclassrooms.mddapi.core.infrastructure.dto;

public record UserResponse(
    Integer id,
    String email,
    String name
) {}
