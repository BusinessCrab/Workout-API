package com.business_crab.Workout_API.model.dto;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkoutScheduleDTO {
    private Long id;
    @NotNull
    @NotBlank
    private Long workoutPlanId;
    @NotNull
    @NotBlank
    private Instant scheduledDate;
    @NotNull
    private Boolean completed;
    private String comments;
}