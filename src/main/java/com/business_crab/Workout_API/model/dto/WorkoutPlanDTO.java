package com.business_crab.Workout_API.model.dto;

import java.time.Instant;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkoutPlanDTO {
    private Long id;
    private Long userId;
    @NotNull
    @NotBlank
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
    private List<WorkoutExerciseDTO> exercises;
    private List<WorkoutScheduleDTO> schedules;
}