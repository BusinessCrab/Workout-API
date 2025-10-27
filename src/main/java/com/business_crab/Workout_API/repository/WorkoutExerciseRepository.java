package com.business_crab.Workout_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.business_crab.Workout_API.model.entity.WorkoutExercise;

public interface WorkoutExerciseRepository extends  JpaRepository<WorkoutExercise, Long> { }