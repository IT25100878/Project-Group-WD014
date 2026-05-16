package com.drivingschool.controller;

import com.drivingschool.model.Vehicle;
import com.drivingschool.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/vehicles")

public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) throws IOException {
        List<Vehicle> vehicles;
        if (search != null && !search.trim().isEmpty()) {
            vehicles = vehicleService.searchVehicles(search);
            model.addAttribute("searchKeyword", search);
        } else {
            vehicles = vehicleService.getAllVehicles();
        }
        model.addAttribute("vehicles", vehicles);
        return "vehicle-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("vehicle", new Vehicle("", "", "", "", "", ""));
        return "vehicle-form";
    }

    @PostMapping("/save")
    public String save(Vehicle vehicle) throws IOException {
        if (vehicle.getId() == null || vehicle.getId().isEmpty()) {
            // Create new vehicle
            List<Vehicle> existing = vehicleService.getAllVehicles();
            int nextId = existing.size() + 1;
            String newId = "VEH" + String.format("%03d", nextId);
            vehicle.setId(newId);
            vehicle.setStatus("Available");
            vehicleService.addVehicle(vehicle);
        } else {
            // Update existing vehicle
            vehicleService.updateVehicle(vehicle);
        }
        return "redirect:/vehicles";
    }


}
