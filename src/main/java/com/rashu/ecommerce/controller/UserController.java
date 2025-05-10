package com.rashu.ecommerce.controller;

import com.rashu.ecommerce.dto.UserRequest;
import com.rashu.ecommerce.dto.UserResponse;
import com.rashu.ecommerce.entity.UserEntity;
import com.rashu.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class    UserController {
    private final UserService userService;
    @GetMapping("/user-info")
    public  ResponseEntity<List<UserResponse>> getAllUsers(){
       return ResponseEntity.ok(userService.fetchAllUsers());
    }
    @GetMapping("/user-info/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
      UserResponse userResponse = userService.fetchUser(id);
        if (userResponse == null){
            return new ResponseEntity<>(userService.fetchUser(id), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userResponse);

    }
    @PostMapping("/add-info")
    public ResponseEntity<String> addUsers(@RequestBody UserRequest userRequest){
        userService.createUsers(userRequest);
        return ResponseEntity.ok("Added successfully");
    }
    @PutMapping("/updated-info/{id}")
    public ResponseEntity<String> modifyUser(@PathVariable("id") Long id, @RequestBody UserEntity updatedName) {
        userService.updateUser(id,updatedName);
        return ResponseEntity.ok("Updated Successfully");
    }
}
