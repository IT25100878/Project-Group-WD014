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

