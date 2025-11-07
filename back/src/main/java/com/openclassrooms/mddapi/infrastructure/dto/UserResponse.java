package com.openclassrooms.mddapi.infrastructure.dto;

public record UserResponse(
    Integer id,
    String email,
    String name
) {}
