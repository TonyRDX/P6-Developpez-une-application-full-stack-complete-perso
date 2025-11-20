package com.openclassrooms.mddapi.core.infrastructure.featuregroup.post;

public record PostSse(
    Integer id,
    Integer topic_id,
    Integer author_id
) {}