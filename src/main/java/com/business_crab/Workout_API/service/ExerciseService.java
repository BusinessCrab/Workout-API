package com.business_crab.Workout_API.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.business_crab.Workout_API.model.entity.Exercise;
import com.business_crab.Workout_API.repository.ExerciseRepository;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseService(final ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Exercise createExercise(final Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public Exercise getExerciseById(final Long id) {
        return exerciseRepository.findById(id)
                                 .orElseThrow(() -> new RuntimeException("Exercise is not found"));
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public Exercise updateExercise(final Long id , final Exercise exerciseDetails) {
        Exercise exercise = getExerciseById(id);
        exercise.setName(exerciseDetails.getName());
        exercise.setDescription(exerciseDetails.getDescription());
        exercise.setCategory(exerciseDetails.getCategory());
        exercise.setMuscleGroup(exerciseDetails.getMuscleGroup());
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(final Long id) {
        final Exercise exercise = getExerciseById(id);
        exerciseRepository.delete(exercise);
    }
}