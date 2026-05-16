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


}
