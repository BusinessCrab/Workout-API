package com.business_crab.Workout_API.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.business_crab.Workout_API.model.dto.ExerciseDTO;
import com.business_crab.Workout_API.model.entity.Exercise;
import com.business_crab.Workout_API.service.ExerciseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/exercises")
@Tag(name="Exercises" , description="Endpoints for managing exercises")
@SecurityRequirement(name="bearerAuth")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(final ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @Operation(summary="Get all exercises" , description="Returns a list of all exercises")
    @ApiResponses(value = {
        @ApiResponse(responseCode="200" , description="Seccessfully retrieved exercises") ,
        @ApiResponse(responseCode="401" , description="Unauthorized - Missing token")
    })
    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> getAllExercises() {
        List<ExerciseDTO> exercises = exerciseService.getAllExercises()
                                                     .stream()
                                                     .map(this::mapToDTO)
                                                     .collect(Collectors.toList());
        return ResponseEntity.ok(exercises);
    }

    @Operation(summary="Create an exercise" , description="Creates a new exercise")
    @ApiResponses(value = {
        @ApiResponse(responseCode="200" , description="Exercise created") ,
        @ApiResponse(responseCode="400" , description="Invalid exercise data")
    })
    @PostMapping
    public ResponseEntity<ExerciseDTO> createExercise(final @RequestBody ExerciseDTO exerciseDTO) {
        Exercise exercise = new Exercise();
        exercise.setName(exerciseDTO.getName());
        exercise.setDescription(exerciseDTO.getDescription());
        exercise.setCategory(exerciseDTO.getCategory());
        exercise.setMuscleGroup(exerciseDTO.getMuscleGroup());
        Exercise savedExercise = exerciseService.createExercise(exercise);
        return ResponseEntity.ok(mapToDTO(savedExercise));
    }

    private ExerciseDTO mapToDTO(Exercise exercise) {
        ExerciseDTO dto = new ExerciseDTO();
        dto.setId(exercise.getId());
        dto.setName(exercise.getName());
        dto.setDescription(exercise.getDescription());
        dto.setCategory(exercise.getCategory());
        dto.setMuscleGroup(exercise.getMuscleGroup());
        return dto;
    }
}
