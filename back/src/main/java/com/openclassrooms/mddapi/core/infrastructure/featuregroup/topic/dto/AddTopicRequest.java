package com.openclassrooms.mddapi.core.infrastructure.featuregroup.topic.dto;

public record AddTopicRequest (
    String title,
    String content
) {}