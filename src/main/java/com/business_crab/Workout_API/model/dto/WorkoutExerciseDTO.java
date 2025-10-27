package com.business_crab.Workout_API.model.dto;

import lombok.Data;

@Data
public class WorkoutExerciseDTO {
    private Long id;
    private Long exerciseId;
    private String exerciseName;
    private Integer sets;
    private Integer repetitions;
    private Double weight;
}