package com.rashu.ecommerce.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String firstname;
    private String lastname;
    private String phoneNo;
    private String email;
    private AddressDTO addressDTO;
}
