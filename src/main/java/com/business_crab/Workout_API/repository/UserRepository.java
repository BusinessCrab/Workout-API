package com.business_crab.Workout_API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.business_crab.Workout_API.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);
}
