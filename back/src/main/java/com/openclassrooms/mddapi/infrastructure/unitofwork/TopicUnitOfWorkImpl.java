package com.openclassrooms.mddapi.infrastructure.unitofwork;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.application.unitofwork.BasicUnitOfWork;
import com.openclassrooms.mddapi.domain.model.Topic;
import com.openclassrooms.mddapi.infrastructure.repository.TopicRepository;

@Component
public class TopicUnitOfWorkImpl implements BasicUnitOfWork<Topic> {
    @Autowired private TopicRepository topicRepository;

    private Topic entity;
    public void register(Topic e) { 
        this.entity = e; 
    }

    public Publisher<Topic> completeAndReturn() {
        return this.topicRepository.save(entity);
    }

    public void load(Class<Topic> type, Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }
}


