package com.business_crab.Workout_API.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="workout_exercises")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkoutExercise {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="workout_plan_id" , nullable=false)
    private WorkoutPlan workoutPlan;
    @ManyToOne
    @JoinColumn(name="exercise_id" , nullable=false)
    private Exercise exercise;
    @Column(nullable=false)
    private Integer sets;
    @Column(nullable=false)
    private Integer repetitions;
    @Column(nullable=false)
    private Double weight;
}