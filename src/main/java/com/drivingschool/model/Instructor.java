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
    @Override
    public String getRole() {
        return "Instructor";
    }

    // Getters and setters (generate via IntelliJ: right-click → Generate → Getter and Setter)
    public String getLicenseNo() { return licenseNo; }
    public void setLicenseNo(String licenseNo) { this.licenseNo = licenseNo; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
