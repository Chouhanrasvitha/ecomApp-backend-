package com.rashu.ecommerce.dto;

import com.rashu.ecommerce.entity.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String phoneNo;
    private String email;
    private UserRole role = UserRole.CUSTOMER;
    private AddressDTO addressDTO;
}
