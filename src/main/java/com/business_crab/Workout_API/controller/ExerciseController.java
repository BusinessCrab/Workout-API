package com.business_crab.Workout_API.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Operation(summary="Updates an exercise" , description="Updates an exercise")
    @ApiResponses(value = {
        @ApiResponse(responseCode="200" , description="Exercise updated") ,
        @ApiResponse(responseCode="400" , description="Invalid exercise data") ,
        @ApiResponse(responseCode="403" , description="Forbidden 403 - Not Owner or Admin")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDTO> updateExercise(final @PathVariable Long id , final @RequestBody ExerciseDTO exerciseDTO) {
        Exercise exercise = new Exercise();
        exercise.setId(exerciseDTO.getId());
        exercise.setName(exerciseDTO.getName());
        exercise.setDescription(exerciseDTO.getDescription());
        exercise.setCategory(exerciseDTO.getCategory());
        exercise.setMuscleGroup(exerciseDTO.getMuscleGroup());
        Exercise updatedExercise = exerciseService.updateExercise(id , exercise);
        return ResponseEntity.ok(mapToDTO(updatedExercise));
    }

    @Operation(summary="Delete an exercise" , description="Deletes an exercise by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode="200" , description="Exercise deleted") ,
        @ApiResponse(responseCode="403" , description="Forbidden 403 - Not Owner or Admin") ,
        @ApiResponse(responseCode="404" , description="Exercise not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(final @PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.ok().build();
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