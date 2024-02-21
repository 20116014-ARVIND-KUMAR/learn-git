package com.learnspringboot.blog.repositories;

import com.learnspringboot.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

}
