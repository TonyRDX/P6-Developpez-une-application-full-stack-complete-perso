package com.openclassrooms.mddapi.core.infrastructure.featuregroup.post.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.core.domain.model.Post;
import com.openclassrooms.mddapi.core.infrastructure.persistence.entity.PostPersistence;

@Component
public class PostMapper {
    @Autowired private ModelMapper modelMapper;
    
    public PostPersistence toPersistence(Post post) {
        PostPersistence dbPost = this.modelMapper.map(
            post, 
            com.openclassrooms.mddapi.core.infrastructure.persistence.entity.PostPersistence.class);
        System.out.println(post);
        return dbPost;
    }

    public Post toDomain(PostPersistence dbPost) {
        Post post = this.modelMapper.map(dbPost, Post.class);
        System.out.println(post);
        return post;
    }
}