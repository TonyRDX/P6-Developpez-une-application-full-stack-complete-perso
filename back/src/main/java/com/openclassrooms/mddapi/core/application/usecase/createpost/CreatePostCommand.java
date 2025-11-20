package com.openclassrooms.mddapi.core.application.usecase.createpost;

public record CreatePostCommand (
    String title,
    String content,
    Integer topic_id,
    Integer user_id
) {}