package com.drivingschool.model;

public class Instructor {
    private String licenseNo;
    private String specialization; // Car, Bike, Both
    private String status;         // Available, Busy, Inactive

    public Instructor(String id, String name, String phone, String licenseNo, String specialization, String status) {
        super(id, name, phone);
        this.licenseNo = licenseNo;
        this.specialization = specialization;
        this.status = status;
    }
}
