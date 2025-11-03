package com.business_crab.Workout_API.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.business_crab.Workout_API.model.dto.WorkoutScheduleDTO;
import com.business_crab.Workout_API.model.entity.WorkoutPlan;
import com.business_crab.Workout_API.model.entity.WorkoutSchedule;
import com.business_crab.Workout_API.service.WorkoutPlanService;
import com.business_crab.Workout_API.service.WorkoutScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/workout-schedules")
@Tag(name="Workout Schedules" , description="Endpoints for managing workout schedules")
@SecurityRequirement(name="bearerAuth")
public class WorkoutScheduleController {
    private final WorkoutScheduleService workoutScheduleService;
    private final WorkoutPlanService workoutPlanService;

    public WorkoutScheduleController(final WorkoutScheduleService workoutScheduleService ,
                                     final WorkoutPlanService workoutPlanService) {
        this.workoutScheduleService = workoutScheduleService;
        this.workoutPlanService = workoutPlanService;
    }

    @Operation(summary = "Get all workout schedules", description = "Returns a list of all workout schedules")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved workout schedules"),
        @ApiResponse(responseCode = "401", description = "Unauthorized access"),
    })
    @GetMapping
    public ResponseEntity<List<WorkoutScheduleDTO>> getAllWorkoutSchedules() {
        List<WorkoutScheduleDTO> schedules = workoutScheduleService.getAllWorkoutSchedule()
                                                                   .stream()
                                                                   .map(this::mapToDTO)
                                                                   .collect(Collectors.toList());
        return ResponseEntity.ok(schedules);
    }

    @Operation(summary = "Get workout schedule by ID", description = "Returns a specific workout schedule by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved workout schedule"),
        @ApiResponse(responseCode = "404", description = "Workout schedule not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized access"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutScheduleDTO> getWorkoutScheduleById(final Long id) {
        final WorkoutSchedule schedule = workoutScheduleService.getWorkoutScheduleById(id);
        return ResponseEntity.ok(mapToDTO(schedule));
    }

    @Operation(summary = "Get the workout schedule of a user" , description = "Returns a list of workout schedules for a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200" , description = "Successfully retrieved workout schedules for the user") ,
        @ApiResponse(responseCode = "404" , description = "User not found") ,
        @ApiResponse(responseCode = "401" , description = "Unauthorized access")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkoutScheduleDTO>> getSchedulesByUser(final Long userId) {
        List<WorkoutScheduleDTO> schedules = workoutScheduleService.getSchedulesByUser(userId)
                                                                   .stream()
                                                                   .map(this::mapToDTO)
                                                                   .collect(Collectors.toList());
        return ResponseEntity.ok(schedules);
    }

    @Operation(summary = "Create a workout schedule", description = "Schedules a workout for a specific plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout schedule created"),
            @ApiResponse(responseCode = "400", description = "Invalid schedule data"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Not the owner or admin")
    })
    @PostMapping
    public ResponseEntity<WorkoutScheduleDTO> createWorkoutSchedule(@Valid @RequestBody WorkoutScheduleDTO workoutScheduleDTO,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        WorkoutSchedule schedule = new WorkoutSchedule();
        schedule.setScheduledDate(workoutScheduleDTO.getScheduledDate());
        schedule.setIsCompleted(workoutScheduleDTO.getIsCompleted());
        schedule.setComments(workoutScheduleDTO.getComments());

        WorkoutPlan workoutPlan = workoutPlanService.getWorkoutPlanById(workoutScheduleDTO.getWorkoutPlanId());
        if (!workoutPlan.getUser().getEmail().equals(userDetails.getUsername()) &&
                userDetails.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        schedule.setWorkoutPlan(workoutPlan);

        WorkoutSchedule savedSchedule = workoutScheduleService.createWorkoutSchedule(schedule);
        return ResponseEntity.ok(mapToDTO(savedSchedule));
    }


    @PutMapping("/{id}")
    public ResponseEntity<WorkoutScheduleDTO> updateWorkoutSchedule(@PathVariable Long id, @RequestBody WorkoutScheduleDTO workoutScheduleDTO) {
        WorkoutSchedule schedule = new WorkoutSchedule();
        schedule.setScheduledDate(workoutScheduleDTO.getScheduledDate());
        schedule.setIsCompleted(workoutScheduleDTO.getIsCompleted());
        schedule.setComments(workoutScheduleDTO.getComments());
        WorkoutSchedule updatedSchedule = workoutScheduleService.updateWorkoutSchedule(id, schedule);
        return ResponseEntity.ok(mapToDTO(updatedSchedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutSchedule(@PathVariable Long id) {
        workoutScheduleService.deleteWorkoutSchedule(id);
        return ResponseEntity.noContent().build();
    }


    private WorkoutScheduleDTO mapToDTO(final WorkoutSchedule schedule) {
        final WorkoutScheduleDTO dto = new WorkoutScheduleDTO();
        dto.setId(schedule.getId());
        dto.setWorkoutPlanId(schedule.getWorkoutPlan().getId());
        dto.setScheduledDate(schedule.getScheduledDate());
        dto.setIsCompleted(schedule.getIsCompleted());
        dto.setComments(schedule.getComments());
        return dto;
    }
}