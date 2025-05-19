package com.rashu.ecommerce.service;

import com.rashu.ecommerce.dto.AddressDTO;
import com.rashu.ecommerce.dto.UserRequest;
import com.rashu.ecommerce.dto.UserResponse;
import com.rashu.ecommerce.entity.Address;
import com.rashu.ecommerce.entity.UserEntity;
import com.rashu.ecommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public UserResponse mapToResponse(UserEntity savedUser) {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(savedUser.getId()));
        response.setFirstname(savedUser.getFirstname());
        response.setLastname(savedUser.getLastname());
        response.setEmail(savedUser.getEmail());
        response.setPhoneNo(savedUser.getPhoneNo());
        response.setRole(savedUser.getRole());
        if (savedUser.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(savedUser.getAddress().getStreet());
            addressDTO.setState(savedUser.getAddress().getState());
            addressDTO.setCity(savedUser.getAddress().getCity());
            addressDTO.setCountry(savedUser.getAddress().getCountry());
            addressDTO.setZipCode(savedUser.getAddress().getZipCode());
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

    public void deleteUserById(Long id) {
        UserEntity existingUser = userRepo.findById(id).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        userRepo.delete(existingUser);
    }
}