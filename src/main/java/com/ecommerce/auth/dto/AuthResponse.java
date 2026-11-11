package com.ecommerce.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String token;
    private String tokenType;
    private long expiresIn;
    private String email;
    private String role;
}
