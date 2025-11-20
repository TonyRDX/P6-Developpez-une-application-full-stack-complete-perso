package com.openclassrooms.mddapi.infrastructure.unitofwork;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.infrastructure.persistence.entity.Topic;
import com.openclassrooms.mddapi.shared.application.unitofwork.BasicUnitOfWork;

@Component
public class TopicUnitOfWorkImpl implements BasicUnitOfWork<Topic> {

    @Override
    public void register(Topic entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public Topic load(Class<Topic> type, Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }

    @Override
    public Publisher<Topic> completeAndReturn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'completeAndReturn'");
    }
    // @Autowired private TopicRepository topicRepository;

    // private Topic entity;
    // public void register(Topic e) { 
    //     this.entity = e; 
    // }

    // public Publisher<Topic> completeAndReturn() {
    //     return this.topicRepository.save(entity);
    // }

    // public Topic load(Class<Topic> type, Integer id) {
    //     throw new UnsupportedOperationException("Unimplemented method 'load'");
    // }
}


