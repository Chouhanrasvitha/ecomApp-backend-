package com.rashu.ecommerce.service;

import com.rashu.ecommerce.entity.UserEntity;
import com.rashu.ecommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public  class UserService  {
    private final UserRepo userRepo;

    public List<UserEntity> fetchAllUsers() {
        return userRepo.findAll();
    }
    public void createUsers(UserEntity userEntity) {
        userRepo.save(userEntity);
    }
}