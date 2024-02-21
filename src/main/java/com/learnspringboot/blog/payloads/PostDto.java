package com.learnspringboot.blog.payloads;

import com.learnspringboot.blog.entities.Category;
import com.learnspringboot.blog.entities.Comment;
import com.learnspringboot.blog.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private Integer postId;
    private String postTitle;

    private String content;

    private String imageName;

    private String addedDate;

    private CategoryDto category;
    private UserDto user;

    private List<CommentDto> comments = new ArrayList<>();
}
