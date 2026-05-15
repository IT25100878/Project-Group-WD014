package com.drivingschool.controller;

import com.drivingschool.model.Schedule;
import com.drivingschool.model.Student;
import com.drivingschool.model.Instructor;
import com.drivingschool.model.Vehicle;
import com.drivingschool.service.ScheduleService;
import com.drivingschool.service.StudentService;
import com.drivingschool.service.InstructorService;
import com.drivingschool.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private VehicleService vehicleService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) throws IOException {
        List<Schedule> schedules;
        if (search != null && !search.trim().isEmpty()) {
            schedules = scheduleService.searchSchedules(search);
            model.addAttribute("searchKeyword", search);
        } else {
            schedules = scheduleService.getAllSchedules();
        }
        model.addAttribute("schedules", schedules);
        return "schedule-list";
    }