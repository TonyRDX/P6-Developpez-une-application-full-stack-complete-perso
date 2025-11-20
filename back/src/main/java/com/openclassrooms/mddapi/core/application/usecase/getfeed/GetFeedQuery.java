package com.openclassrooms.mddapi.core.application.usecase.getfeed;


public record GetFeedQuery
 (
    Integer userId,
    Boolean recentFirst
) {}
