package com.openclassrooms.mddapi.application;

public record CreatePostCommand (
    String title,
    String content,
    Integer topic_id,
    Integer user_id
) {}