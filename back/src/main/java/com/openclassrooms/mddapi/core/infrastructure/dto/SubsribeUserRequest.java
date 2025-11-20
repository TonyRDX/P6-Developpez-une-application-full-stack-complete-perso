package com.openclassrooms.mddapi.core.infrastructure.dto;

public record SubsribeUserRequest (
    String email,
    String name,
    String password
) {}