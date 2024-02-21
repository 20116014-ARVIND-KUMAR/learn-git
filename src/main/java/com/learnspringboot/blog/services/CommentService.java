package com.learnspringboot.blog.services;

import com.learnspringboot.blog.payloads.CommentDto;

public interface CommentService {

    CommentDto addComment(CommentDto commentDto, Integer postId, Integer userId);
    void deleteComment(Integer commentId);
}
