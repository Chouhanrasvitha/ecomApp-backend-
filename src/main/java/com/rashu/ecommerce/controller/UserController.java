package com.rashu.ecommerce.controller;

import com.rashu.ecommerce.dto.UserRequest;
import com.rashu.ecommerce.dto.UserResponse;
import com.rashu.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    @GetMapping("/user-info")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.FOUND);
    }
    @GetMapping("/user-info/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.fetchUserById(id), HttpStatus.FOUND);
    }

    @PostMapping("/add-info")
    public ResponseEntity<UserResponse> addUsers(@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.createUsers(userRequest), HttpStatus.CREATED);
    }
    @PutMapping("/updated-info/{id}")
    public ResponseEntity<Optional<UserResponse>> modifiedUsers(@PathVariable  Long id, @RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.updateUsers(id,userRequest), HttpStatus.OK);
    }
}
