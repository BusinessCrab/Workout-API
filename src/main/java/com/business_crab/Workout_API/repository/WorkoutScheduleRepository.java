package com.business_crab.Workout_API.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.business_crab.Workout_API.model.entity.WorkoutSchedule;

public interface WorkoutScheduleRepository extends JpaRepository<WorkoutSchedule, Long> {
    List<WorkoutSchedule> findByUserId(final Long userId);
    List<WorkoutSchedule> findByWorkoutPlanIdOrderByScheduleDateAsc(final Long workoutPlanId);
    List<WorkoutSchedule> findByWorkoutPlanUserEmailAndCompletedTrueOrderByScheduledDateDesc(final String email);
    List<WorkoutSchedule> findByWorkoutPlanUserEmailAndCompletedTrueAndScheduledDateBetweenOrderByScheduledDateDesc(
                        final String email, 
                        final Instant startDate,
                        final Instant endDate);
}