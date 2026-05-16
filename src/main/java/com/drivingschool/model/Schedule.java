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

    public String toFileString() {
        return id + "|" + studentId + "|" + instructorId + "|" + vehicleId + "|" +
                startTime + "|" + endTime + "|" + status;
    }