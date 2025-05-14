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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepo userRepo;

    public List<UserResponse> fetchAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    public UserResponse fetchUserById(Long id){
        return userRepo.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(()-> new RuntimeException("id entered is not found"));
    }
    public UserResponse createUsers(UserRequest userRequest){
        UserEntity user = new UserEntity();
        updateRequestToEntity(user,userRequest);
        UserEntity savedUser = userRepo.save(user);
        return mapToResponse(savedUser);
    }
    public Optional<UserResponse> updateUsers(Long id, UserRequest updateduserRequest) {
        if (userRepo.existsById(id)) {
            UserEntity existingUser = userRepo.findById(id).orElseThrow(() -> new RuntimeException(" id entered is not found"));
            updateRequestToEntity(existingUser, updateduserRequest);
            UserEntity savedUpdatedUserRequest = userRepo.save(existingUser);
            return Optional.ofNullable(mapToResponse(savedUpdatedUserRequest));
        }
        return null;
    }
    public UserResponse mapToResponse(UserEntity user) {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setEmail(user.getEmail());
        response.setPhoneNo(user.getPhoneNo());
        response.setRole(user.getRole());
                if (user.getAddress()!=null){
                    AddressDTO addressDTO = new AddressDTO();
                    addressDTO.setStreet(user.getAddress().getStreet());
                    addressDTO.setState(user.getAddress().getState());
                    addressDTO.setCity(user.getAddress().getCity());
                    addressDTO.setCountry(user.getAddress().getCountry());
                    addressDTO.setZipCode(user.getAddress().getZipCode());
                    response.setAddressDTO(addressDTO);
                }
                return response;
    }
    public   void updateRequestToEntity(UserEntity user, UserRequest userRequest) {
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNo(userRequest.getPhoneNo());
        if (userRequest.getAddressDTO()!=null){
            Address address = new Address();
            address.setStreet(userRequest.getAddressDTO().getStreet());
            address.setState(userRequest.getAddressDTO().getState());
            address.setCity(userRequest.getAddressDTO().getCity());
            address.setCountry(userRequest.getAddressDTO().getCountry());
            address.setZipCode(userRequest.getAddressDTO().getZipCode());
            user.setAddress(address);
        }
    }
}