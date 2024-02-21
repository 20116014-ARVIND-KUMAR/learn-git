package com.learnspringboot.blog.services;

import com.learnspringboot.blog.entities.User;
import com.learnspringboot.blog.exceptions.ResourceNotFoundException;
import com.learnspringboot.blog.payloads.UserDto;
import com.learnspringboot.blog.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private UserRepo userRepo;

    private ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepo theUserRepo, ModelMapper theMapper){
        this.userRepo = theUserRepo;
        this.mapper=theMapper;
    }


    @Override
    public UserDto createUser(UserDto user) {

        User user1 = dtoToEntity(user);

        User savedUser = this.userRepo.save(user1);

        return entityToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto user, Integer id) {

           User user1 = this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", user.getId()));

            user1.setName(user.getName());
            user1.setPassword(user.getPassword());
            user1.setEmail(user.getEmail());
            user1.setAbout(user.getAbout());

            User upadatedUser = this.userRepo.save(user1);

        return entityToDto(upadatedUser);
    }

    @Override
    public UserDto getUser(int id) {

        User user=this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));

        return entityToDto(user);
    }
    @Override
    public List<UserDto> getAllUser() {
       List<User> userList = this.userRepo.findAll();
//        System.out.println(userList.get(0).getPosts().get(0).getPostId());
       List<UserDto> userDtoList = userList.stream().map(this::entityToDto).toList();

       return userDtoList;

    }

    @Override
    public void deleteUser(int id) {
        User user=this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));

        this.userRepo.delete(user);
    }

    private User dtoToEntity(UserDto userDto){

        User user = this.mapper.map(userDto, User.class);

        return user;

    }

    private UserDto entityToDto(User user){

        UserDto userDto = this.mapper.map(user, UserDto.class);

        return userDto;
    }
}
