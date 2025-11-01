package com.business_crab.Workout_API.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.business_crab.Workout_API.model.dto.WorkoutExerciseDTO;
import com.business_crab.Workout_API.model.dto.WorkoutPlanDTO;
import com.business_crab.Workout_API.model.dto.WorkoutScheduleDTO;
import com.business_crab.Workout_API.model.entity.WorkoutExercise;
import com.business_crab.Workout_API.model.entity.WorkoutPlan;
import com.business_crab.Workout_API.model.entity.WorkoutSchedule;
import com.business_crab.Workout_API.service.WorkoutPlanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/workout-plans")
@Tag(name="Workout Plans" , description="Endpoints for managing workout plans")
@SecurityRequirement(name="bearerAuth")
public class WorkoutPlanController {
    private final WorkoutPlanService workoutPlanService;

    public WorkoutPlanController(final WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @Operation(summary="Get all workout plans" , description="Returns a list of all workout plans")
    @ApiResponses(value = {
        @ApiResponse(responseCode="200" , description="Successfully retrieved workout plans") ,
        @ApiResponse(responseCode="401" , description="Unauthorized - Missing token")
    })
    @GetMapping
    public ResponseEntity<List<WorkoutPlanDTO>> getAllWorkoutPlans(@AuthenticationPrincipal UserDetails userDetails) {
        List<WorkoutPlan> plans = workoutPlanService.getWorkoutPlansByUserEmail(userDetails.getUsername());
        return ResponseEntity.ok(plans.stream()
                                      .map(this::mapToDTO)
                                      .collect(Collectors.toList()));
    }

    @Operation(summary="Get workout plan by ID" , description="Returns a workout plan by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode="200" , description="Successfully retrieved workout plan") ,
        @ApiResponse(responseCode="401" , description="Unauthorized - Missing token") ,
        @ApiResponse(responseCode="404" , description="Workout plan not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlanDTO> getWorkoutPlanById(final @PathVariable Long id ,
                                                             final @AuthenticationPrincipal UserDetails userDetails)
    {
        final WorkoutPlan workoutPlan = workoutPlanService.getWorkoutPlanById(id);
        if (!workoutPlan.getUser().getEmail().equals(userDetails.getUsername()) &&
            userDetails.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return ResponseEntity.ok(mapToDTO(workoutPlan));
            }
            return ResponseEntity.ok(mapToDTO(workoutPlan));
    }

    

    private WorkoutPlanDTO mapToDTO(final WorkoutPlan workoutPlan) {
        WorkoutPlanDTO dto = new WorkoutPlanDTO();
        dto.setId(workoutPlan.getId());
        dto.setUserId(workoutPlan.getUser() != null ? workoutPlan.getUser().getId() : null);
        dto.setName(workoutPlan.getName());
        dto.setCreatedAt(workoutPlan.getCreatedAt());
        dto.setUpdatedAt(workoutPlan.getUpdatedAt());
        dto.setExercises(workoutPlan.getWorkoutExercises() != null ?
                workoutPlan.getWorkoutExercises().stream()
                        .map(this::mapToWorkoutExerciseDTO)
                        .collect(Collectors.toList()) : Collections.emptyList());
        dto.setSchedules(workoutPlan.getWorkoutSchedules() != null ?
                workoutPlan.getWorkoutSchedules().stream()
                        .map(this::mapToWorkoutScheduleDTO)
                        .sorted((s1, s2) -> s1.getScheduledDate().compareTo(s2.getScheduledDate()))
                        .collect(Collectors.toList()) : Collections.emptyList());
        return dto;
    }
    private WorkoutScheduleDTO mapToWorkoutScheduleDTO(WorkoutSchedule workoutSchedule) {
        WorkoutScheduleDTO dto = new WorkoutScheduleDTO();
        dto.setId(workoutSchedule.getId());
        dto.setWorkoutPlanId(workoutSchedule.getWorkoutPlan().getId());
        dto.setScheduledDate(workoutSchedule.getScheduledDate());
        dto.setCompleted(workoutSchedule.getIsCompleted());
        dto.setComments(workoutSchedule.getComments());
        return dto;
    }

    private WorkoutExerciseDTO mapToWorkoutExerciseDTO(WorkoutExercise workoutExercise) {
        WorkoutExerciseDTO dto = new WorkoutExerciseDTO();
        dto.setId(workoutExercise.getId());
        dto.setExerciseId(workoutExercise.getExercise().getId());
        dto.setExerciseName(workoutExercise.getExercise().getName());
        dto.setSets(workoutExercise.getSets());
        dto.setRepetitions(workoutExercise.getRepetitions());
        dto.setWeight(workoutExercise.getWeight());
        return dto;
    }
}
