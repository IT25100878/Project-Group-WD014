package com.drivingschool.service;

import com.drivingschool.model.Schedule;
import com.drivingschool.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private FileHandler fileHandler;
    private final String FILE_NAME = "schedules.txt";

    public void addSchedule(Schedule schedule) throws IOException {
        fileHandler.appendLine(FILE_NAME, schedule.toFileString());
    }

    public List<Schedule> getAllSchedules() throws IOException {
        List<String> lines = fileHandler.readAllLines(FILE_NAME);
        List<Schedule> list = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                list.add(Schedule.fromFileString(line));
            }
        }
        return list;
    }

    public Schedule getScheduleById(String id) throws IOException {
        return getAllSchedules().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateSchedule(Schedule updated) throws IOException {
        List<Schedule> list = getAllSchedules();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updated.getId())) {
                list.set(i, updated);
                break;
            }
        }
        saveAll(list);
    }

    public void deleteSchedule(String id) throws IOException {
        List<Schedule> filtered = getAllSchedules().stream()
                .filter(s -> !s.getId().equals(id))
                .collect(Collectors.toList());
        saveAll(filtered);
    }

    private void saveAll(List<Schedule> list) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Schedule s : list) {
            lines.add(s.toFileString());
        }
        fileHandler.writeAllLines(FILE_NAME, lines);
    }

    public List<Schedule> searchSchedules(String keyword) throws IOException {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllSchedules();
        }
        String lowerKeyword = keyword.toLowerCase().trim();
        return getAllSchedules().stream()
                .filter(s -> s.getId().toLowerCase().contains(lowerKeyword) ||
                        s.getStudentId().toLowerCase().contains(lowerKeyword) ||
                        s.getInstructorId().toLowerCase().contains(lowerKeyword) ||
                        s.getStatus().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
}

