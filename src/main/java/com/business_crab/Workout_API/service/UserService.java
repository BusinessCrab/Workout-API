package com.business_crab.Workout_API.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.business_crab.Workout_API.model.entity.User;
import com.business_crab.Workout_API.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(final User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(final Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new RuntimeException("User is not found"));
    }

    public User updateUser(final Long id , final User userDetails) {
        final User user = getUserById(id);
        if (userDetails.getFirstName() != null) {
            user.setFirstName(userDetails.getFirstName());
        }
        if (userDetails.getLastName() != null) {
            user.setLastName(userDetails.getLastName());
        }
        return userRepository.save(user);
    }

    public void deleteUser(final Long id) {
        final User user = getUserById(id);
        userRepository.delete(user);
    }
}