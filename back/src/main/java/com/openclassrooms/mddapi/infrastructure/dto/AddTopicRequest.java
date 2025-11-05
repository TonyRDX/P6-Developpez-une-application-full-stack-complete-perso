package com.openclassrooms.mddapi.infrastructure.dto;

public record AddTopicRequest (
    String title,
    String content
) {}