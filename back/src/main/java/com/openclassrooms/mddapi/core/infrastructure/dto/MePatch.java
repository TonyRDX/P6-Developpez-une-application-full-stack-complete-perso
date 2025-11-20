package com.openclassrooms.mddapi.core.infrastructure.dto;

public record MePatch(
    String email,
    String name,
    String password
) {}
