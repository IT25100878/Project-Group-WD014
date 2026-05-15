package com.drivingschool.model;

import java.time.LocalDateTime;

public class Schedule {
    private String id;
    private String studentId;
    private String instructorId;
    private String vehicleId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;     // Booked, Completed, Cancelled

    public Schedule(String id, String studentId, String instructorId, String vehicleId,
                    LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.id = id;
        this.studentId = studentId;
        this.instructorId = instructorId;
        this.vehicleId = vehicleId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }
