package com.openclassrooms.mddapi.infrastructure.dto;

public record AddPostRequest (
    String title,
    String content,
    Integer topic_id
) {}