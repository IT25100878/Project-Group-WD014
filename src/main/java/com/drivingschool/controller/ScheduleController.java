package com.drivingschool.controller;

import com.drivingschool.model.Schedule;

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

/**
 * ScheduleController handles all schedule-related requests.
 *
 * Responsibilities:
 * - Display schedules
 * - Search schedules
 * - Show create form
 * - Save new schedules
 * - Update existing schedules
 * - Delete schedules
 */
@Controller // Marks this class as a Spring MVC Controller
@RequestMapping("/schedules") // Base URL for all schedule operations
public class ScheduleController {

    // =========================================================
    // SERVICE DEPENDENCIES
    // =========================================================


    /**
     * Service used for schedule operations
     */
    @Autowired
    private ScheduleService scheduleService;

    /**
     * Service used to manage students
     */
    @Autowired
    private StudentService studentService;

    /**
     * Service used to manage instructors
     */
    @Autowired
    private InstructorService instructorService;

    /**
     * Service used to manage vehicles
     */
    @Autowired
    private VehicleService vehicleService;

    // =========================================================
    // DATE & TIME FORMATTER
    // =========================================================

    /**
     * Formatter used for HTML datetime-local input fields.
     *
     * Example format:
     * 2026-05-16T14:30
     */
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    // =========================================================
    // DISPLAY ALL SCHEDULES
    // =========================================================

    /**
     * Displays all schedules.
     *
     * Also supports searching schedules using a keyword.
     *
     * URL:
     * GET /schedules
     *
     * Example:
     * /schedules?search=SCH001
     */
    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) throws IOException {

        // List to store schedules
        List<Schedule> schedules;

        // Check if search keyword is provided
        if (search != null && !search.trim().isEmpty()) {

            // Search schedules
            schedules = scheduleService.searchSchedules(search);

            // Send search keyword back to view
            model.addAttribute("searchKeyword", search);
        } else {
            // Load all schedules if no search
            schedules = scheduleService.getAllSchedules();
        }
        // Add schedules to model
        model.addAttribute("schedules", schedules);

        // Return schedule list page
        return "schedule-list";
    }

    // =========================================================
    // SHOW CREATE FORM
    // =========================================================

    /**
     * Displays the schedule creation form.
     *
     * URL:
     * GET /schedules/new
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) throws IOException {

        // Create empty schedule object with default values
        Schedule empty = new Schedule("", "", "", "", LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Booked");

        // Add empty schedule object to model
        model.addAttribute("schedule", empty);

        // Format start and end times for HTML form fields
        model.addAttribute("startTimeStr", empty.getStartTime().format(formatter));
        model.addAttribute("endTimeStr", empty.getEndTime().format(formatter));

        // =====================================================
        // ADD DROPDOWN DATA
        // =====================================================

        /**
         * Load all students for student dropdown
         */
        model.addAttribute("students", studentService.getAllStudents());

        /**
         * Load all instructors for instructor dropdown
         */
        model.addAttribute("instructors", instructorService.getAllInstructors());

        /**
         * Load all vehicles for vehicle dropdown
         */
        model.addAttribute("vehicles", vehicleService.getAllVehicles());

        // Return schedule form page
        return "schedule-form";
    }

    // =========================================================
    // SHOW EDIT FORM
    // =========================================================

    /**
     * Displays the edit form for a selected schedule.
     *
     * URL:
     * GET /schedules/edit/{id}
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) throws IOException {

        // Get schedule using ID
        Schedule schedule = scheduleService.getScheduleById(id);

        // Add schedule to model
        model.addAttribute("schedule", schedule);

        // Format start time for form
        model.addAttribute("startTimeStr", schedule.getStartTime().format(formatter));

        // Format end time for form
        model.addAttribute("endTimeStr", schedule.getEndTime().format(formatter));

        // =====================================================
        // LOAD DROPDOWN DATA FOR EDITING
        // =====================================================

        // Load all students
        model.addAttribute("students", studentService.getAllStudents());

        // Load all instructors
        model.addAttribute("instructors", instructorService.getAllInstructors());

        // Load all vehicles
        model.addAttribute("vehicles", vehicleService.getAllVehicles());

        // Open schedule form page
        return "schedule-form";
    }

    // =========================================================
    // SAVE OR UPDATE SCHEDULE
    // =========================================================

    /**
     * Saves a new schedule or updates an existing schedule.
     *
     * URL:
     * POST /schedules/save
     */
    @PostMapping("/save")
    public String save(@RequestParam String id,
                       @RequestParam String studentId,
                       @RequestParam String instructorId,
                       @RequestParam String vehicleId,
                       @RequestParam String startTime,
                       @RequestParam String endTime,
                       @RequestParam String status) throws IOException {

        // Convert start time string into LocalDateTime
        LocalDateTime start = LocalDateTime.parse(startTime, formatter);

        // Convert end time string into LocalDateTime
        LocalDateTime end = LocalDateTime.parse(endTime, formatter);

        // Create schedule object
        Schedule schedule = new Schedule(id, studentId, instructorId, vehicleId, start, end, status);

        // =====================================================
        // CHECK WHETHER NEW OR EXISTING SCHEDULE
        // =====================================================

        // If ID is empty -> create new schedule
        if (schedule.getId() == null || schedule.getId().isEmpty()) {

            // =====================================================
// CREATE OPERATION - GENERATE UNIQUE SCHEDULE ID
// =====================================================

// Get all schedules from the database/service
// This is needed to find the highest existing ID
            List<Schedule> existing = scheduleService.getAllSchedules();

// Variable to store the maximum numeric ID found
// Initial value is 0
            int maxId = 0;

// =====================================================
// LOOP THROUGH EACH EXISTING SCHEDULE
// =====================================================
            for (Schedule s : existing) {

                // Get the current schedule ID
                // Example: SCH001
                String sid = s.getId();

                // =================================================
                // CHECK:
                // 1. ID should not be null
                // 2. ID should start with "SCH"
                // =================================================
                if (sid != null && sid.startsWith("SCH")) {

                    try {

                        // =============================================
                        // EXTRACT NUMERIC PART FROM ID
                        //
                        // Example:
                        // SCH001 → 001
                        // SCH025 → 025
                        // =============================================
                        String numberPart = sid.substring(3);

                        // Convert string number into integer
                        // Example:
                        // "001" → 1
                        int num = Integer.parseInt(numberPart);

                        // =============================================
                        // CHECK WHETHER CURRENT NUMBER
                        // IS GREATER THAN maxId
                        // =============================================
                        if (num > maxId)

                            // Update maxId with larger value
                            maxId = num;

                    } catch (NumberFormatException ignored) {

                        // =============================================
                        // IGNORE INVALID ID FORMATS
                        //
                        // Examples:
                        // SCHABC
                        // SCH12A
                        // SCH-01
                        // =============================================

                    }
                }
            }

// =====================================================
// GENERATE NEXT ID NUMBER
//
// Example:
// maxId = 15
// nextId = 16
// =====================================================
            int nextId = maxId + 1;

// =====================================================
// CREATE NEW FORMATTED SCHEDULE ID
//
// %03d means:
// - Minimum 3 digits
// - Add leading zeros if necessary
//
// Examples:
// 1  → 001
// 7  → 007
// 25 → 025
// =====================================================
            String newId = "SCH" + String.format("%03d", nextId);

// Set generated ID to schedule object
            schedule.setId(newId);

// =====================================================
// SAVE NEW SCHEDULE INTO DATABASE/SYSTEM
// =====================================================
            scheduleService.addSchedule(schedule);

        } else {

            // =================================================
            // UPDATE EXISTING SCHEDULE
            //
            // If ID already exists,
            // this is not a new record
            // =================================================
            scheduleService.updateSchedule(schedule);
        }

// =====================================================
// REDIRECT USER TO SCHEDULE LIST PAGE
// AFTER SAVE OR UPDATE
// =====================================================
        return "redirect:/schedules";
    }

    // =========================================================
    // DELETE SCHEDULE
    // =========================================================

    /**
     * Deletes a schedule using its ID.
     *
     * URL:
     * GET /schedules/delete/{id}
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) throws IOException {

        // Delete schedule
        scheduleService.deleteSchedule(id);

        // Redirect to schedule list page
        return "redirect:/schedules";
    }
}

