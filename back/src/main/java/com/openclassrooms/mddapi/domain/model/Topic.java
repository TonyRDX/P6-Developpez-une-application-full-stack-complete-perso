package com.openclassrooms.mddapi.domain.model;

import java.time.Instant;

public class Topic {

    private Integer id;
    private String title;
    private String content;
    private Instant createdAt;
    private boolean isSubscribed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void subscribe() {
        this.isSubscribed = true;
    }

    public void unsubscribe() {
        this.isSubscribed = false;
    }
}