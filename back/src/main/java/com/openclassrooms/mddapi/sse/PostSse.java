package com.openclassrooms.mddapi.sse;

public record PostSse(
        Integer id,
        String title,
        String content,
        String theme
) {}