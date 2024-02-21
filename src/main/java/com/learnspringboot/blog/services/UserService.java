package com.learnspringboot.blog.services;


import com.learnspringboot.blog.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer id);
    UserDto getUser(int id);

    List<UserDto> getAllUser();

    void deleteUser(int id);
}
