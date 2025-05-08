package com.rashu.ecommerce.service;

import com.rashu.ecommerce.entity.UserEntity;
import com.rashu.ecommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Service
@RequiredArgsConstructor
public  class UserService  {
    private final UserRepo userRepo;

    public List<UserEntity> fetchAllUsers() {
        return userRepo.findAll();
    }
    public UserEntity fetchUser(Long id){
       if (id == null){
           return null;
       }
        return userRepo.findById(id).orElse(null);
    }
    public void createUsers(UserEntity userEntity) {
        userRepo.save(userEntity);
    }
}