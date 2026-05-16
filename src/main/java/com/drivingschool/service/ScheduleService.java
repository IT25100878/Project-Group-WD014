package com.drivingschool.service;

import com.drivingschool.model.Schedule;
import com.drivingschool.util.FileHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ScheduleService handles all business logic related to Schedule.
 *
 * Responsibilities:
 * - Add schedule
 * - Retrieve schedules
 * - Update schedule
 * - Delete schedule
 * - Search schedule
 *
 * Data is stored in a text file (schedules.txt).
 */
@Service
public class ScheduleService {

    // =========================================================
    // FILE HANDLER DEPENDENCY
    // =========================================================

    /**
     * Utility class used for file operations
     * (read, write, append).
     */
    @Autowired
    private FileHandler fileHandler;

    /**
     * File name used to store schedule data
     */
    private final String FILE_NAME = "schedules.txt";

    // =========================================================
    // ADD SCHEDULE
    // =========================================================

    /**
     * Adds a new schedule by appending it to the file.
     */
    public void addSchedule(Schedule schedule) throws IOException {
        fileHandler.appendLine(FILE_NAME, schedule.toFileString());
    }

    // =========================================================
    // GET ALL SCHEDULES
    // =========================================================

    /**
     * Reads all schedules from file and converts them
     * into Schedule objects.
     */
    public List<Schedule> getAllSchedules() throws IOException {

        // Read all lines from file
        List<String> lines = fileHandler.readAllLines(FILE_NAME);

        // List to store Schedule objects
        List<Schedule> list = new ArrayList<>();

        // Convert each line into Schedule object
        for (String line : lines) {

            // Skip empty lines
            if (!line.trim().isEmpty()) {
                list.add(Schedule.fromFileString(line));
            }
        }
        return list;
    }

    // =========================================================
    // GET SCHEDULE BY ID
    // =========================================================

    /**
     * Finds a schedule using its ID.
     *
     * Returns null if not found.
     */
    public Schedule getScheduleById(String id) throws IOException {
        return getAllSchedules().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // =========================================================
    // UPDATE SCHEDULE
    // =========================================================

    /**
     * Updates an existing schedule.
     *
     * Steps:
     * - Load all schedules
     * - Find matching ID
     * - Replace object
     * - Save back to file
     */

    public void updateSchedule(Schedule updated) throws IOException {

        // Get all schedules
        List<Schedule> list = getAllSchedules();

        // Loop through list to find matching ID
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updated.getId())) {

                // Replace old schedule with updated one
                list.set(i, updated);
                break;
            }
        }
        // Save updated list to file
        saveAll(list);
    }

    // =========================================================
    // DELETE SCHEDULE
    // =========================================================

    /**
     * Deletes a schedule by filtering it out.
     */
    public void deleteSchedule(String id) throws IOException {

        // Remove schedule with matching ID
        List<Schedule> filtered = getAllSchedules().stream()
                .filter(s -> !s.getId().equals(id))
                .collect(Collectors.toList());

        // Save updated list
        saveAll(filtered);
    }


    // =========================================================
    // SAVE ALL SCHEDULES (PRIVATE HELPER)
    // =========================================================

    /**
     * Writes full schedule list back to file.
     *
     * Used after update or delete operations.
     */
    private void saveAll(List<Schedule> list) throws IOException {
        List<String> lines = new ArrayList<>();

        // Convert each schedule to string format
        for (Schedule s : list) {
            lines.add(s.toFileString());
        }

        // Write all lines to file (overwrite)
        fileHandler.writeAllLines(FILE_NAME, lines);
    }


    // =========================================================
    // SEARCH SCHEDULES
    // =========================================================

    /**
     * Searches schedules based on keyword.
     *
     * Search fields:
     * - Schedule ID
     * - Student ID
     * - Instructor ID
     * - Status
     */
    public List<Schedule> searchSchedules(String keyword)
            throws IOException {

        // If keyword is empty, return all schedules
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllSchedules();
        }

        // Convert keyword to lowercase for case-insensitive search
        String lowerKeyword = keyword.toLowerCase().trim();
        return getAllSchedules().stream()
                .filter(s -> s.getId().toLowerCase().contains(lowerKeyword) ||
                        s.getStudentId().toLowerCase().contains(lowerKeyword) ||
                        s.getInstructorId().toLowerCase().contains(lowerKeyword) ||
                        s.getStatus().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
}

