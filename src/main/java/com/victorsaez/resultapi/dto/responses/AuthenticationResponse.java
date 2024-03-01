package com.victorsaez.resultapi.dto.responses;

import com.victorsaez.resultapi.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String jwt;
    private UserDTO user;
}