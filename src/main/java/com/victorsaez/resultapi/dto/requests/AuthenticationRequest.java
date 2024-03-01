package com.victorsaez.resultapi.dto.requests;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}