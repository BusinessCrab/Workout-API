package com.business_crab.Workout_API.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.business_crab.Workout_API.model.entity.WorkoutPlan;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findByUserEmail(final String email);
}