package com.learnspringboot.blog.services;

import com.learnspringboot.blog.entities.Comment;
import com.learnspringboot.blog.entities.Post;
import com.learnspringboot.blog.entities.User;
import com.learnspringboot.blog.exceptions.ResourceNotFoundException;
import com.learnspringboot.blog.payloads.CommentDto;
import com.learnspringboot.blog.payloads.PostDto;
import com.learnspringboot.blog.payloads.PostResponse;
import com.learnspringboot.blog.repositories.CommentRepo;
import com.learnspringboot.blog.repositories.PostRepo;
import com.learnspringboot.blog.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    private PostRepo postRepo;
    private CommentRepo commentRepo;

    private UserRepo userRepo;

    private ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(PostRepo postRepo, CommentRepo commentRepo, ModelMapper mapper, UserRepo userRepo){
        this.commentRepo=commentRepo;
        this.postRepo=postRepo;
        this.mapper=mapper;
        this.userRepo= userRepo;
    }

    @Override
    public CommentDto addComment(CommentDto commentDto, Integer postId, Integer userId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId", postId));
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));

        Comment comment = this.mapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = this.commentRepo.save(comment);
        return this.mapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
         Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));

         this.commentRepo.delete(comment);
    }
}
