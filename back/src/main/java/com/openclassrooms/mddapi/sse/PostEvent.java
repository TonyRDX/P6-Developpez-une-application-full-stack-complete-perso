package com.openclassrooms.mddapi.sse;

import java.time.Instant;

public class PostEvent {

    private String type;
    private Integer postId;
    private Instant timestamp;

    public PostEvent(String type, Integer postId, Instant timestamp) {
        this.type = type;
        this.postId = postId;
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public Integer getPostId() {
        return postId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
