package com.learnspringboot.blog.repositories;

import com.learnspringboot.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
