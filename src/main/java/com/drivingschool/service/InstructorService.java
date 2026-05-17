package com.drivingschool.service;
import com.drivingschool.model.Instructor;
import com.drivingschool.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    @Autowired
    private FileHandler fileHandler;
    private final String FILE_NAME = "instructors.txt";

    public void addInstructor(Instructor instructor) throws IOException {
        fileHandler.appendLine(FILE_NAME, instructor.toFileString());
    }

    public List<Instructor> getAllInstructors() throws IOException {
        List<String> lines = fileHandler.readAllLines(FILE_NAME);
        List<Instructor> list = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                list.add(Instructor.fromFileString(line));
            }
        }
        return list;
    }
    public Instructor getInstructorById(String id) throws IOException {
        return getAllInstructors().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateInstructor(Instructor updated) throws IOException {
        List<Instructor> list = getAllInstructors();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updated.getId())) {
                list.set(i, updated);
                break;
            }
        }
        saveAll(list);
    }
    public void deleteInstructor(String id) throws IOException {
        List<Instructor> filtered = getAllInstructors().stream()
                .filter(i -> !i.getId().equals(id))
                .collect(Collectors.toList());
        saveAll(filtered);
    }

    private void saveAll(List<Instructor> list) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Instructor i : list) {
            lines.add(i.toFileString());
        }
        fileHandler.writeAllLines(FILE_NAME, lines);
    }
}
