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
            // CREATE – generate unique ID
            List<Vehicle> existing = vehicleService.getAllVehicles();
            int maxId = 0;
            for (Vehicle v : existing) {
                String id = v.getId();
                if (id != null && id.startsWith("VEH")) {
                    try {
                        int num = Integer.parseInt(id.substring(3));
                        if (num > maxId) maxId = num;
                    } catch (NumberFormatException ignored) {}
                }
            }
            int nextId = maxId + 1;
            String newId = "VEH" + String.format("%03d", nextId);
            vehicle.setId(newId);
            vehicle.setStatus("Available");
            vehicleService.addVehicle(vehicle);
        } else {
            // UPDATE
            vehicleService.updateVehicle(vehicle);
        }
        return "redirect:/vehicles";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) throws IOException {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicle-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) throws IOException {
        vehicleService.deleteVehicle(id);
        return "redirect:/vehicles";
    }


}
