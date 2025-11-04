package com.business_crab.Workout_API.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.business_crab.Workout_API.model.dto.UserCreateDTO;
import com.business_crab.Workout_API.model.dto.UserDTO;
import com.business_crab.Workout_API.model.entity.User;
import com.business_crab.Workout_API.security.JwtUtil;
import com.business_crab.Workout_API.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name="Authentication" , description="Endpoints for user authentication")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(final AuthService authService ,
                          final AuthenticationManager authenticationManager ,
                          final UserDetailsService userDetailsService ,
                          final JwtUtil jwtUtil)
    {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Sign up a new user" , description = "Creates a new user and returns user details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200" , description = "User created successfully") ,
        @ApiResponse(responseCode = "400" , description = "Invalid user data")
    })
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(final @Valid @RequestBody UserCreateDTO userCreateDTO) {
        final User user = authService.signUp(userCreateDTO);
        return ResponseEntity.ok(mapToDTO(user));
    }

    @Operation(summary = "Log in a user" , description = "Authenticates a user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" , description = "Login successful, token returned"),
            @ApiResponse(responseCode = "401" , description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(final @Valid @RequestBody UserCreateDTO loginRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }

    private UserDTO mapToDTO(final User user) {
        final UserDTO dto = new UserDTO();
        dto.setId(user.getId());
                dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}