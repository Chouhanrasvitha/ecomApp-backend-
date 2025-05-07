package com.rashu.ecommerce.controller;

import com.rashu.ecommerce.entity.UserEntity;
import com.rashu.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/user-info")
    public List<UserEntity> getAllUsers(){
       return userService.fetchAllUsers();
    }
    @PostMapping("/add-info")
    public void addUsers(@RequestBody UserEntity userEntity){
        userService.createUsers(userEntity);
        System.out.println("Users are added");
    }
}
