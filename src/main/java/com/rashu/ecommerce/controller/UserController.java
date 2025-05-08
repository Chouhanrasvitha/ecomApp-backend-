package com.rashu.ecommerce.controller;

import com.rashu.ecommerce.entity.UserEntity;
import com.rashu.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/user-info")
    public  ResponseEntity<List<UserEntity>> getAllUsers(){
       return ResponseEntity.ok(userService.fetchAllUsers());
    }
    @GetMapping("/user-info/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable Long id){
        UserEntity user = userService.fetchUser(id);
        if (user == null){
            return new ResponseEntity<>(userService.fetchUser(id), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userService.fetchUser(id));
    }
    @PostMapping("/add-info")
    public ResponseEntity<String> addUsers(@RequestBody UserEntity userEntity){
        userService.createUsers(userEntity);
        return ResponseEntity.ok("Added successfully");
    }
}
