package com.openclassrooms.mddapi.core.infrastructure.dto;

import java.time.Instant;

public record SinglePostFeed (
    Integer id,
    String title,
    String content,
    String author_name,
    Instant created_at,
    Integer topic_id
){}
