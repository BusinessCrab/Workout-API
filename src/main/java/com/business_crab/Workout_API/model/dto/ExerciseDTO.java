package com.business_crab.Workout_API.model.dto;

import com.business_crab.Workout_API.model.entity.Category;
import com.business_crab.Workout_API.model.entity.MuscleGroup;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExerciseDTO {
    private Long id;
    @NonNull
    @NotBlank
    private String name;
    private String description;
    private Category category;
    private MuscleGroup muscleGroup;
}