package com.business_crab.Workout_API.model.entity;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="workout_plans")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id" , nullable=false)
    private User user;
    @Column(nullable=false)
    private String name;
    @Column(name="created_at" , nullable=false)
    private Instant createdAt = Instant.now();
    @Column(name="updated_at" , nullable=true)
    private Instant updateAt;
    @OneToMany(mappedBy="workout_plan" , cascade=CascadeType.ALL , orphanRemoval=true)
    private List<WorkoutExercise> workoutExercises;
    @OneToMany(mappedBy="workout_schedule" , cascade=CascadeType.ALL , orphanRemoval=true)
    private List<WorkoutSchedule> workoutSchedules;
}
