package com.openclassrooms.mddapi.infrastructure.dto;

public record MePatch(
    String email,
    String name,
    String password
) {}
