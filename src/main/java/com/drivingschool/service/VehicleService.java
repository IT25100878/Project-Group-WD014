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

    public Vehicle getVehicleById(String id) throws IOException {
        return getAllVehicles().stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateVehicle(Vehicle updated) throws IOException {
        List<Vehicle> list = getAllVehicles();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updated.getId())) {
                list.set(i, updated);
                break;
            }
        }
        saveAll(list);
    }

    public void deleteVehicle(String id) throws IOException {
        List<Vehicle> filtered = getAllVehicles().stream()
                .filter(v -> !v.getId().equals(id))
                .collect(Collectors.toList());
        saveAll(filtered);
    }
    private void saveAll(List<Vehicle> list) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Vehicle v : list) {
            lines.add(v.toFileString());
        }
        fileHandler.writeAllLines(FILE_NAME, lines);
    }

    public List<Vehicle> searchVehicles(String keyword) throws IOException {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllVehicles();
        }
        String lowerKeyword = keyword.toLowerCase().trim();
        return getAllVehicles().stream()
                .filter(v -> v.getId().toLowerCase().contains(lowerKeyword) ||
                        v.getRegNo().toLowerCase().contains(lowerKeyword) ||
                        v.getModel().toLowerCase().contains(lowerKeyword) ||
                        v.getType().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

}
