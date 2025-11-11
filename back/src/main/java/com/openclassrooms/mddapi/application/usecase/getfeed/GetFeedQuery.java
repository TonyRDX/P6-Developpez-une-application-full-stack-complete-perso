package com.openclassrooms.mddapi.application.usecase.getfeed;


public record GetFeedQuery
 (
    Integer userId,
    Boolean recentFirst
) {}
