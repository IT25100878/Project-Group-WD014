package com.drivingschool.model;

public class Vehicle {

    private String id;
    private String regNo;      // Registration number
    private String model;
    private String type;       // Car, Motorcycle
    private String fuelType;   // Petrol, Diesel, Electric
    private String status;     // Available, Maintenance, Assigned

    public Vehicle(String id, String regNo, String model, String type, String fuelType, String status) {
        this.id = id;
        this.regNo = regNo;
        this.model = model;
        this.type = type;
        this.fuelType = fuelType;
        this.status = status;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}
