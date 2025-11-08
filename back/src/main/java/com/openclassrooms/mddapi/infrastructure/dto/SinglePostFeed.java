package com.openclassrooms.mddapi.infrastructure.dto;

import java.time.Instant;

public record SinglePostFeed (
    Integer id,
    String author,
    String title,
    String content,
    Instant created_at
){}
