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

}
