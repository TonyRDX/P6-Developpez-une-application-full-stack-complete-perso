package com.openclassrooms.mddapi.infrastructure.featuregroup.post;

public record PostSse(
        Integer id,
        String title,
        String content,
        Integer topicId
) {}