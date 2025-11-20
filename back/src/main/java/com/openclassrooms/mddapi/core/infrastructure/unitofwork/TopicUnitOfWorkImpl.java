package com.openclassrooms.mddapi.core.infrastructure.unitofwork;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.TopicPersistence;
import com.openclassrooms.mddapi.shared.application.unitofwork.BasicUnitOfWork;

@Component
public class TopicUnitOfWorkImpl implements BasicUnitOfWork<TopicPersistence> {

    @Override
    public void register(TopicPersistence entity) {
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public TopicPersistence load(Class<TopicPersistence> type, Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }

    @Override
    public Publisher<TopicPersistence> completeAndReturn() {
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


