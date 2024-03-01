package com.victorsaez.resultapi.controllers;

import com.victorsaez.resultapi.config.CustomUserDetails;
import com.victorsaez.resultapi.config.JwtUtil;
import com.victorsaez.resultapi.dto.UserDTO;
import com.victorsaez.resultapi.dto.requests.AuthenticationRequest;
import com.victorsaez.resultapi.dto.requests.SignupRequest;
import com.victorsaez.resultapi.dto.responses.AuthenticationResponse;

import com.victorsaez.resultapi.entities.User;
import com.victorsaez.resultapi.mappers.UserMapper;
import com.victorsaez.resultapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "Authentication")
@RequestMapping(value = "/v1/authentication", produces = "application/json")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    private UserService userService;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    private JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @Operation(summary = "Logs in a user.")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);

        return ResponseEntity.ok().headers(headers).body(new AuthenticationResponse(jwt, userMapper.userToUserDTO(((CustomUserDetails) userDetails).getUser())));
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @Operation(summary = "Register in a user.")
    public ResponseEntity<AuthenticationResponse> signup(@Valid @RequestBody SignupRequest signupRequest) throws Exception {

        UserDTO user = userService.register(signupRequest);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signupRequest.getUsername(), signupRequest.getPassword()));

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(signupRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);

        return ResponseEntity.ok().headers(headers).body(new AuthenticationResponse(jwt, userMapper.userToUserDTO(((CustomUserDetails) userDetails).getUser())));
    }
}