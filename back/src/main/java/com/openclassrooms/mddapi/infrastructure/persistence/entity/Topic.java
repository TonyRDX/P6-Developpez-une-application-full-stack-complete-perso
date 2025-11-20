package com.openclassrooms.mddapi.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.openclassrooms.mddapi.shared.domain.model.Entity;

@Table("topics")
public class Topic implements Entity {

    @Id
    private Integer id;

    private String title;
    private String content;

    @Column("created_at")
    private Instant createdAt;
    @Column("user_id")
    private Integer userId;

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

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isSubscribed() {
        return (this.userId != null);
    }
}