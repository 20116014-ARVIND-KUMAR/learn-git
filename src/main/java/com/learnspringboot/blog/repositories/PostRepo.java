package com.learnspringboot.blog.repositories;

import com.learnspringboot.blog.entities.Category;
import com.learnspringboot.blog.entities.Post;
import com.learnspringboot.blog.entities.User;
import com.learnspringboot.blog.payloads.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepo extends JpaRepository<Post, Integer> {

    Page<Post> findAllByUser(User user, Pageable pageable);
    Page<Post> findAllByCategory(Category category, Pageable pageable);

    List<Post> findByPostTitleContaining(String title);
}
