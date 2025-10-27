package com.business_crab.Workout_API.model.entity;

import java.time.Instant;

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
@Table(name="workout_schedules")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkoutSchedule {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="workout_plan_id" , nullable=false)
    private WorkoutPlan workoutPlan;
    @Column(name="schedule_date" , nullable=false)
    private Instant scheduleDate;
    @Column(nullable=false)
    private Boolean isCompleted = false;
    @Column(nullable=true)
    private String comments;
}