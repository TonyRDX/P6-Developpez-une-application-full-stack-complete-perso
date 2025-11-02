package com.openclassrooms.mddapi.dto;

public record AddTopicRequest (
    String title,
    String content
) {}