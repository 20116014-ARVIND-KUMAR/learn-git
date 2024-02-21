package com.learnspringboot.blog.controllers;

import com.learnspringboot.blog.payloads.CommentDto;
import com.learnspringboot.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }

    @PostMapping("comments/user/{userId}/post/{postId}")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto,
                                                 @PathVariable Integer postId, @PathVariable Integer userId){

        CommentDto commentDto1 = this.commentService.addComment(commentDto,postId, userId);
        return new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentID){

         this.commentService.deleteComment(commentID);
         return new ResponseEntity<>(Map.of("message","comment is successfully deleted" ), HttpStatus.OK);
    }

}
