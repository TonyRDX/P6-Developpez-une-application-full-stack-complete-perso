package com.openclassrooms.mddapi.dto;

public record AddPostRequest (
    String title,
    String content,
    Integer theme
) {}