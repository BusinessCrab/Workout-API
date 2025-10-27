package com.business_crab.Workout_API.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="exercises")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Exercise {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String name;
    @Column(nullable=true)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Category category;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private MuscleGroup muscleGroup;
    @OneToMany(mappedBy="workout_exercises")
    private List<WorkoutExercise> workoutExercises;
}