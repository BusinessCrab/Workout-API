package com.business_crab.Workout_API.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.business_crab.Workout_API.model.entity.WorkoutExercise;
import com.business_crab.Workout_API.repository.WorkoutExerciseRepository;


@Service
public class WorkoutExerciseService {
    private final WorkoutExerciseRepository workoutExerciseRepository;

    public WorkoutExerciseService(final WorkoutExerciseRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    public WorkoutExercise createWorkoutExercise(final WorkoutExercise workoutExercise) {
        return workoutExerciseRepository.save(workoutExercise);
    }

    public WorkoutExercise getWorkoutExerciseById(final Long id) {
        return workoutExerciseRepository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("WorkoutExercise is not found"));
    }

    public List<WorkoutExercise> getAllWorkoutExercises() {
        return workoutExerciseRepository.findAll();
    }

    public WorkoutExercise updateWorkoutExercise(final Long id , final WorkoutExercise workoutExerciseDetails) {
        WorkoutExercise workoutExercise = getWorkoutExerciseById(id);
        workoutExercise.setSets(workoutExerciseDetails.getSets());
        workoutExercise.setRepetitions(workoutExerciseDetails.getRepetitions());
        workoutExercise.setWeight(workoutExerciseDetails.getWeight());
        return workoutExerciseRepository.save(workoutExercise);
    }

    public void deleteWorkoutExercise(final Long id) {
        final WorkoutExercise workoutExercise = getWorkoutExerciseById(id);
        workoutExerciseRepository.delete(workoutExercise);
    }
}