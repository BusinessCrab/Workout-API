package com.business_crab.Workout_API.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.business_crab.Workout_API.model.entity.WorkoutSchedule;
import com.business_crab.Workout_API.repository.WorkoutScheduleRepository;


@Service
public class WorkoutScheduleService {
    private final WorkoutScheduleRepository workoutScheduleRepository;
    private final UserService userService;

    public WorkoutScheduleService(final WorkoutScheduleRepository workoutScheduleRepository ,
                                  final UserService userService) 
    {
        this.workoutScheduleRepository = workoutScheduleRepository;
        this.userService = userService;
    }

    public WorkoutSchedule createWorkoutSchedule(final WorkoutSchedule workoutSchedule) {
        return workoutScheduleRepository.save(workoutSchedule);
    }

    public WorkoutSchedule getWorkoutScheduleById(final Long id) {
        return workoutScheduleRepository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("WorkoutSchedule is not found"));
    }

    public List<WorkoutSchedule> getAllWorkoutSchedule() {
        return workoutScheduleRepository.findAll();
    }

    public WorkoutSchedule updateWorkoutSchedule(final Long id ,
                                                 final WorkoutSchedule workoutScheduleDetails)
    {
        WorkoutSchedule workoutSchedule = getWorkoutScheduleById(id);
        workoutSchedule.setScheduleDate(workoutScheduleDetails.getScheduleDate());
        workoutSchedule.setIsCompleted(workoutScheduleDetails.getIsCompleted());
        workoutSchedule.setComments(workoutScheduleDetails.getComments());
        return workoutScheduleRepository.save(workoutSchedule);
    }

    public void deleteWorkoutSchedule(final Long id) {
        final WorkoutSchedule workoutSchedule = getWorkoutScheduleById(id);
        workoutScheduleRepository.delete(workoutSchedule);
    }

    public List<WorkoutSchedule> getSchedulesByUser(final Long userId) {
        userService.getUserById(userId);
        return workoutScheduleRepository.findByUserId(userId);
    }

    public List<WorkoutSchedule> getPastWorkoutsByUserEmail(final String email ,
                                                            final Instant startDate ,
                                                            final Instant endDate)
    {
        if (startDate == null && endDate == null) {
            return workoutScheduleRepository.findByWorkoutPlanUserEmailAndCompletedTrueOrderByScheduledDateDesc(email);
        }
        return workoutScheduleRepository.findByWorkoutPlanUserEmailAndCompletedTrueAndScheduledDateBetweenOrderByScheduledDateDesc(email, startDate, endDate);
    }
}