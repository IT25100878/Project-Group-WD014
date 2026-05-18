package com.drivingschool.model;


public class Student extends Person {
    private String nic;
    private String address;
    private String licenseType;
    private String status;
    private String password;

    // Constructor
    public Student(String id, String name, String phone, String nic, String address,
                   String licenseType, String status, String password) {
        super(id, name, phone);
        this.nic = nic;
        this.address = address;
        this.licenseType = licenseType;
        this.status = status;
        this.password = password;
    }

    // Getters and Setters
    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    // Convert object to a pipe-separated line
    public String toFileString() {
        return id + "|" + name + "|" + phone + "|" + nic + "|" + address + "|" + licenseType + "|" + status + "|" + password;
    }

    // Create object from a pipe-separated line
    public static Student fromFileString(String line) {
        String[] parts = line.split("\\|");
        // Expecting exactly 8 parts
        return new Student(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
    }
}
