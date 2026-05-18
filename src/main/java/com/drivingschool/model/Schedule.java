package com.drivingschool.model;

import java.time.LocalDateTime;

/**
 * Schedule class represents a driving lesson booking between
 * a student, instructor, and vehicle.
 * It stores timing details and status of the booking.
 */

public class Schedule {

    // Unique identifier for the schedule (e.g., SCH001)
    private String id;

    // ID of the student assigned to this schedule
    private String studentId;

    // ID of the instructor assigned to this schedule
    private String instructorId;

    // ID of the vehicle used in this schedule
    private String vehicleId;

    // Start time of the scheduled lesson
    private LocalDateTime startTime;

    // End time of the scheduled lesson
    private LocalDateTime endTime;

    // Status of the schedule (Booked, Completed, Cancelled)
    private String status;

    //Constructor to initialize all fields of Schedule
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

    // Getters and Setters (generate via IntelliJ)
    public String getId() {
        return id; }
    public void setId(String id) {
        this.id = id; }
    public String getStudentId() {
        return studentId; }
    public void setStudentId(String studentId) {
        this.studentId = studentId; }
    public String getInstructorId() {
        return instructorId; }
    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId; }
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId; }
    public LocalDateTime getStartTime() {
        return startTime; }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime; }
    public LocalDateTime getEndTime() {
        return endTime; }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime; }
    public String getStatus() {
        return status; }
    public void setStatus(String status) {
        this.status = status; }

    // ================= FILE STORAGE METHODS =================

    /**
     * Converts Schedule object into a pipe-separated string
     * for saving into a file.
     */
    public String toFileString() {
        return id + "|" + studentId + "|" + instructorId + "|" + vehicleId + "|" +
                startTime + "|" + endTime + "|" + status;
    }

    /**
     * Creates a Schedule object from a pipe-separated file string.
     * Used when reading data from a file.
     */
    public static Schedule fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Schedule(parts[0], parts[1], parts[2], parts[3],
                LocalDateTime.parse(parts[4]), LocalDateTime.parse(parts[5]), parts[6]);
    }
}