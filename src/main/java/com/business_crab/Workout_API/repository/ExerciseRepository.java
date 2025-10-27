package com.business_crab.Workout_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.business_crab.Workout_API.model.entity.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> { }