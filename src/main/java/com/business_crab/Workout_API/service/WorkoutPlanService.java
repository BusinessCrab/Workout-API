package com.business_crab.Workout_API.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.business_crab.Workout_API.model.entity.Exercise;
import com.business_crab.Workout_API.model.entity.User;
import com.business_crab.Workout_API.model.entity.WorkoutExercise;
import com.business_crab.Workout_API.model.entity.WorkoutPlan;
import com.business_crab.Workout_API.repository.UserRepository;
import com.business_crab.Workout_API.repository.WorkoutExerciseRepository;
import com.business_crab.Workout_API.repository.WorkoutPlanRepository;

import jakarta.transaction.Transactional;

@Service
public class WorkoutPlanService {
    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final ExerciseService exerciseService;
    private final UserRepository userRepository;

    public WorkoutPlanService(final WorkoutPlanRepository workoutPlanRepository ,
            final WorkoutExerciseRepository workoutExerciseRepository ,
            final ExerciseService exerciseService ,
            final UserRepository userRepository)
    {
        this.workoutPlanRepository = workoutPlanRepository;
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.exerciseService = exerciseService;
        this.userRepository = userRepository;
    }

    public WorkoutPlan createWorkoutPlan(final WorkoutPlan workoutPlan) {
        return workoutPlanRepository.save(workoutPlan);
    }

    public WorkoutPlan getWorkoutPlanById(final Long id) {
        return workoutPlanRepository.findById(id)
                                    .orElseThrow(() -> new RuntimeException("WorkoutPlan is not found"));
    }

    public List<WorkoutPlan> getAllWorkoutPlans() {
        return workoutPlanRepository.findAll();
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email)
                             .orElseThrow(() -> new RuntimeException("User is not found"));
    }

    public List<WorkoutPlan> getWorkoutPlansByUserEmail(final String email) {
        return workoutPlanRepository.findByUserEmail(email);
    }

    public WorkoutPlan updateWorkoutPlan(final Long id , final WorkoutPlan workoutPlanDetails) {
        WorkoutPlan workoutPlan = getWorkoutPlanById(id);
        workoutPlan.setName(workoutPlanDetails.getName());
        workoutPlan.setUpdateAt(workoutPlanDetails.getUpdateAt());
        return workoutPlanRepository.save(workoutPlan);
    }

    public void deleteWorkoutPlan(final Long id) {
        final WorkoutPlan workoutPlan = getWorkoutPlanById(id);
        workoutPlanRepository.delete(workoutPlan);
    }

    @Transactional
    public boolean hasExercises(final Long workoutPlanId) {
        final WorkoutPlan workoutPlan = getWorkoutPlanById(workoutPlanId);
        return !workoutPlan.getWorkoutExercises().isEmpty();
    }

    public WorkoutExercise addExerciseToWorkoutPlan(final Long workoutPlanId , 
                                                    final Long exerciseId ,
                                                    final int sets ,
                                                    final int repetitions ,
                                                    final Double weight) 
    {
        final WorkoutPlan workoutPlan = getWorkoutPlanById(workoutPlanId);
        final Exercise exercise = exerciseService.getExerciseById(exerciseId);

        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setWorkoutPlan(workoutPlan);
        workoutExercise.setExercise(exercise);
        workoutExercise.setSets(sets);
        workoutExercise.setRepetitions(repetitions);
        workoutExercise.setWeight(weight);

        return workoutExerciseRepository.save(workoutExercise);
    }
}