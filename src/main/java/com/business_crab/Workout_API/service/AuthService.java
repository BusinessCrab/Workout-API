package com.business_crab.Workout_API.service;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.business_crab.Workout_API.model.dto.UserCreateDTO;
import com.business_crab.Workout_API.model.entity.User;
import com.business_crab.Workout_API.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(final UserRepository userRepository ,
                       final PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(final UserCreateDTO userCreatDTO) {
        final User user = new User();
        user.setFirstName(userCreatDTO.getFirstName());
        user.setLastName(userCreatDTO.getLastName());
        user.setEmail(userCreatDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userCreatDTO.getPassword()));
        if ("admin@hotmail.com".equals(userCreatDTO.getEmail())) {
            user.setRoles(Arrays.asList("ROLE_USER" , "ROLE_ADMIN"));
        } else {
            user.setRoles(Collections.singletonList("ROLE_USER"));
        }
        return userRepository.save(user);
    }
}