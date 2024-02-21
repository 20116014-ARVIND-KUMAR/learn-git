package com.learnspringboot.blog.services;

import com.learnspringboot.blog.entities.Post;
import com.learnspringboot.blog.payloads.PostDto;
import com.learnspringboot.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostDto getPostById(Integer postId);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String fieldValue, String sortDir);

    PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize );

    PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize);

    List<PostDto> searchPost(String keyword);

}
