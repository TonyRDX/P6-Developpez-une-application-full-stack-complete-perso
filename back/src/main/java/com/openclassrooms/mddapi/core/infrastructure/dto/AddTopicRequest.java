package com.openclassrooms.mddapi.core.infrastructure.dto;

public record AddTopicRequest (
    String title,
    String content
) {}