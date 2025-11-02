package com.business_crab.Workout_API.model.dto;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class WorkoutReportDTO {
    private Long workoutPlanId;
    private String workoutPlanName;
    private Instant scheduledDate;
    private Boolean isCompleted;
    private String comments;
    private List<WorkoutExerciseDTO> exercises;
}