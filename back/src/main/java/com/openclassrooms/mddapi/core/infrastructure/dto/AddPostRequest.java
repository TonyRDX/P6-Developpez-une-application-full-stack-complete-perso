package com.openclassrooms.mddapi.core.infrastructure.dto;

public record AddPostRequest (
    String title,
    String content,
    Integer topic_id
) {}