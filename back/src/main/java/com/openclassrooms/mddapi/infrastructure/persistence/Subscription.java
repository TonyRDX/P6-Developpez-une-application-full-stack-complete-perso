package com.openclassrooms.mddapi.infrastructure.persistence;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.openclassrooms.mddapi.shared.domain.model.Entity;

@Table("subscriptions")
public class Subscription implements Entity {
    @Column("topic_id")
    private Integer topicId;

    @Column("user_id")
    private Integer userId;

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
