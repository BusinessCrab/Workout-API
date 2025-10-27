package com.business_crab.Workout_API.model.entity;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="first_name" , nullable=false)
    private String firstName;
    @Column(name="last_name" , nullable=false)
    private String lastName;
    @Column(nullable=false)
    private String email;
    @Column(nullable=false)
    private String password;
    @Column(name="created_at" , nullable=false)
    private Instant createdAt = Instant.now();
    @OneToMany(mappedBy="user" , cascade=CascadeType.ALL , orphanRemoval=true)
    private List<WorkoutPlan> workoutPlans;
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="user_roles" , joinColumns=@JoinColumn(name="user_id"))
    @Column(name="role")
    private List<String> roles;
}