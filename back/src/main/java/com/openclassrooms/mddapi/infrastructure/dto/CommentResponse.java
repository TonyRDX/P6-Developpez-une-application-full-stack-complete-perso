package com.openclassrooms.mddapi.infrastructure.dto;

import java.time.Instant;

public record CommentResponse (
    String content,
    Instant createdAt,
    String author_name
) {}