package com.business_crab.Workout_API.controller;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.business_crab.Workout_API.model.dto.WorkoutExerciseDTO;
import com.business_crab.Workout_API.model.dto.WorkoutReportDTO;
import com.business_crab.Workout_API.model.entity.WorkoutSchedule;
import com.business_crab.Workout_API.service.WorkoutScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reports")
@Tag(name ="Reports" , description="Endpoints for generating workout reports")
@SecurityRequirement(name="bearerAuth")
public class WorkoutReportController {
    private final WorkoutScheduleService workoutScheduleService;

    public WorkoutReportController(final WorkoutScheduleService workoutScheduleService) {
        this.workoutScheduleService = workoutScheduleService;
    }

    @Operation(summary="Get past workout reports" , description="Returns a list of completed workouts")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200" , description = "Successfully retrieved past workout reports") ,
        @ApiResponse(responseCode = "400" , description = "Invalid input parameters") ,
        @ApiResponse(responseCode = "401" , description = "Unauthorized access") ,
        @ApiResponse(responseCode = "500" , description = "Internal server error")
    })
    @GetMapping("/past-workouts")
    public ResponseEntity<List<WorkoutReportDTO>> getPastWorkouts(final @AuthenticationPrincipal UserDetails userDetails ,
                                                                  final @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate ,
                                                                  final @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endTime)
    {
        final List<WorkoutSchedule> schedules = workoutScheduleService.getPastWorkoutsByUserEmail(userDetails.getUsername() , startDate, endTime);
        final List<WorkoutReportDTO> report = schedules.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(report);
    }

    private WorkoutReportDTO mapToDTO(final WorkoutSchedule workoutSchedule) {
        final WorkoutReportDTO dto = new WorkoutReportDTO();
        dto.setWorkoutPlanId(workoutSchedule.getWorkoutPlan().getId());
        dto.setWorkoutPlanName(workoutSchedule.getWorkoutPlan().getName());
        dto.setScheduledDate(workoutSchedule.getScheduledDate());
        dto.setIsCompleted(workoutSchedule.getIsCompleted());
        dto.setComments(workoutSchedule.getComments());
        dto.setExercises(workoutSchedule.getWorkoutPlan().getWorkoutExercises().stream()
                .map(ex -> {
                    WorkoutExerciseDTO exDto = new WorkoutExerciseDTO();
                    exDto.setId(ex.getId());
                    exDto.setExerciseId(ex.getExercise().getId());
                    exDto.setExerciseName(ex.getExercise().getName());
                    exDto.setSets(ex.getSets());
                    exDto.setRepetitions(ex.getRepetitions());
                    exDto.setWeight(ex.getWeight());
                    return exDto;
                })
                .collect(Collectors.toList()));
        return dto;
    }
}