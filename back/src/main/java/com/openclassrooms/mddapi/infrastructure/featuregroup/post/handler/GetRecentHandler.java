package com.openclassrooms.mddapi.infrastructure.featuregroup.post.handler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.domain.model.Post;
import com.openclassrooms.mddapi.infrastructure.featuregroup.post.mapper.PostMapper;
import com.openclassrooms.mddapi.infrastructure.persistence.repository.PostRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GetRecentHandler {
    private final PostRepository postRepository;
    private final PostMapper mapper;

    public GetRecentHandler(
        PostRepository postRepository,
        PostMapper mapper
    ) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    public Flux<Post> getRecent(Mono<List<Integer>> topicIds) {
        return topicIds.flatMapMany(ids ->
            ids.isEmpty() ?
                Flux.empty() : 
                postRepository.findAllByTopicIdIn(ids)
        ).map(mapper::toDomain);
    }
}
