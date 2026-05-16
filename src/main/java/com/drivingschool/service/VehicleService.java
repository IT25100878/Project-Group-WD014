package com.drivingschool.service;

import com.drivingschool.model.Vehicle;
import com.drivingschool.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private FileHandler fileHandler;
    private final String FILE_NAME = "vehicles.txt";

    public void addVehicle(Vehicle vehicle) throws IOException {
        fileHandler.appendLine(FILE_NAME, vehicle.toFileString());
    }

    public List<Vehicle> getAllVehicles() throws IOException {
        List<String> lines = fileHandler.readAllLines(FILE_NAME);
        List<Vehicle> list = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                list.add(Vehicle.fromFileString(line));
            }
        }
        return list;
    }

}
