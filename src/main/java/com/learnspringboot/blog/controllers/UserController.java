package com.learnspringboot.blog.controllers;

import com.learnspringboot.blog.payloads.UserDto;
import com.learnspringboot.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;



    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){

         UserDto createdUser = this.userService.createUser(userDto);

         return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId){

        UserDto userDto1 = this.userService.updateUser(userDto, userId);

        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId){

        this.userService.deleteUser(userId);

        return new ResponseEntity<>(Map.of("message", "User deleted successfully with id: " + userId), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUser(){

        return new ResponseEntity<>(this.userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getAllUser(@PathVariable int userId){

        return new ResponseEntity<>(this.userService.getUser(userId), HttpStatus.OK);
    }

}
