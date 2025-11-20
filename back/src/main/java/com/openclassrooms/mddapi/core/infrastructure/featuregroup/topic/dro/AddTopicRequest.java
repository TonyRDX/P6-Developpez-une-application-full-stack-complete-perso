package com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.dro;

public record AddTopicRequest (
    String title,
    String content
) {}