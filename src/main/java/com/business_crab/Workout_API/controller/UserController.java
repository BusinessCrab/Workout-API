package com.business_crab.Workout_API.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.business_crab.Workout_API.model.dto.UserDTO;
import com.business_crab.Workout_API.model.entity.User;
import com.business_crab.Workout_API.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("api/users")
@Tag(name="Users" , description="Endpoints for managing users")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Operation(summary="Get all users" , description="Returns a list of all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode="200" , description="Successfully retrieved users") ,
        @ApiResponse(responseCode="403" , description="Forbidden - Requires ROLE_ADMIN")
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers()
                                         .stream()
                                         .map(this::mapToDTO)
                                         .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Operation(summary="Get the user by ID" , description="Returns a specific user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode="200" , description="User is found") ,
        @ApiResponse(responseCode="404" , description="User is not found") ,
        @ApiResponse(responseCode="403" , description="Forbidden - Requires ROLE_ADMIN")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(final @PathVariable Long id) {
        final User user = userService.getUserById(id);
        return ResponseEntity.ok(mapToDTO(user));
    }

    @Operation(summary="Update user" , description="Updates an exsiting user")
    @ApiResponses(value = {
        @ApiResponse(responseCode="200" , description="User is updated") ,
        @ApiResponse(responseCode="404" , description="User is not found") ,
        @ApiResponse(responseCode="403" , description="Forbidden - Requires ROLE_ADMIN")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(final @PathVariable Long id , @Valid @RequestBody UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(mapToDTO(updatedUser));
    }

    @Operation(summary="Delete user" , description="Deletes a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode="200" , description="User is deleted") ,
        @ApiResponse(responseCode="404" , description="User is not found") ,
        @ApiResponse(responseCode="403" , description="Forbidden - Requires ROLE_ADMIN")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(final @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private UserDTO mapToDTO(final User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}