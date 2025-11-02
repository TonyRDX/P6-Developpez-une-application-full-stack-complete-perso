package com.openclassrooms.mddapi.dto;

public record AddPostRequest (
    String content,
    String title,
    String theme
) {}