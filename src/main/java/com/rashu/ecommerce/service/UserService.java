package com.rashu.ecommerce.service;

import com.rashu.ecommerce.dto.AddressDTO;
import com.rashu.ecommerce.dto.UserRequest;
import com.rashu.ecommerce.dto.UserResponse;
import com.rashu.ecommerce.entity.Address;
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
        System.out.println(userRepo.findAll().stream().map(this::mapToUserResponse));
        return userRepo.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }
   public UserResponse fetchUser(Long id){
        return userRepo.findById(id)
                .map(this::mapToUserResponse)
                .orElse(null);
   }
    public void createUsers(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        updatedUser(userEntity,userRequest);
        userRepo.save(userEntity);
    }

    public void updateUser(Long id, UserRequest updatedUserRequest){
        if(userRepo.existsById(id)){
            UserEntity existingUserEntity = userRepo.findById(id).
                    orElseThrow(()-> new RuntimeException("the id entered is not found"));
            updatedUser(existingUserEntity,updatedUserRequest);
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
    private void updatedUser(UserEntity userEntity, UserRequest userRequest) {
        userEntity.setFirstname(userRequest.getFirstname());
        userEntity.setLastname(userRequest.getLastname());
        userEntity.setPhoneNo(userRequest.getPhoneNo());
        userEntity.setEmail(userRequest.getEmail());
        if (userRequest.getAddressDTO()!=null){
            Address address = new Address();
            address.setStreet(userEntity.getAddress().getStreet());
            address.setCity(userEntity.getAddress().getCity());
            address.setState(userEntity.getAddress().getState());
            address.setZipCode(userEntity.getAddress().getZipCode());
            address.setCountry(userEntity.getAddress().getCountry());
        }
    }
}