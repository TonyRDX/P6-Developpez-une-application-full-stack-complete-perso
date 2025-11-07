package com.openclassrooms.mddapi.infrastructure.dto;

public record SubsribeUserRequest (
    String email,
    String name,
    String password
) {}