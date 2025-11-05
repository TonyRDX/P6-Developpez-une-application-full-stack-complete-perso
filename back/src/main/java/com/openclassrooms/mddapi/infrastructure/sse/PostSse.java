package com.openclassrooms.mddapi.infrastructure.sse;

public record PostSse(
        Integer id,
        String title,
        String content,
        Integer topicId
) {}