package com.rashu.ecommerce.service;

import com.rashu.ecommerce.dto.AddressDTO;
import com.rashu.ecommerce.dto.UserRequest;
import com.rashu.ecommerce.dto.UserResponse;
import com.rashu.ecommerce.entity.UserEntity;
import com.rashu.ecommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepo userRepo;

    public List<UserResponse> fetchAllUsers() {
        return userRepo.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }
   public UserResponse fetchUser(Long id){
        return userRepo.findById(id)
                .map(this::mapToUserResponse)
                .orElse(null);
   }
    public String createUsers(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstname(userRequest.getFirstname());
        userEntity.setLastname(userRequest.getLastname());
        userEntity.setPhoneNo(userRequest.getPhoneNo());
        userEntity.setEmail(userRequest.getEmail());
        if (userEntity.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(userEntity.getAddress().getStreet());
            addressDTO.setCity(userEntity.getAddress().getCity());
            addressDTO.setState(userEntity.getAddress().getState());
            addressDTO.setZipCode(userEntity.getAddress().getZipCode());
            addressDTO.setCountry(userEntity.getAddress().getCountry());
            userRequest.setAddressDTO(addressDTO);
        }
        userRepo.save(userEntity);
        return "Users added successfully";
    }
    public void updateUser(Long id, UserEntity updatedName){
        if(userRepo.existsById(id)){
            UserEntity existingUserEntity = userRepo.findById(id).
                    orElseThrow(()-> new RuntimeException("the id entered is not found"));
            existingUserEntity.setFirstname(updatedName.getFirstname());
            existingUserEntity.setLastname(updatedName.getLastname());
            existingUserEntity.setPhoneNo(updatedName.getPhoneNo());
            existingUserEntity.setEmail(updatedName.getEmail());
            existingUserEntity.setAddress(updatedName.getAddress());
            userRepo.save(existingUserEntity);
        }
    }
    public UserResponse mapToUserResponse(UserEntity userEntity){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(userEntity.getId()));
        userResponse.setFirstname(userEntity.getFirstname());
        userResponse.setLastname(userEntity.getLastname());
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setPhoneNo(userEntity.getPhoneNo());
        userResponse.setRole(userEntity.getRole());
        if (userEntity.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(userEntity.getAddress().getStreet());
            addressDTO.setCity(userEntity.getAddress().getCity());
            addressDTO.setState(userEntity.getAddress().getState());
            addressDTO.setZipCode(userEntity.getAddress().getZipCode());
            addressDTO.setCountry(userEntity.getAddress().getCountry());
            userResponse.setAddressDTO(addressDTO);
        }
        return userResponse;
    }
}