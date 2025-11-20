package com.openclassrooms.mddapi.core.infrastructure.featuregroup.post.dto;

public record AddPostRequest (
    String title,
    String content,
    Integer topic_id
) {}