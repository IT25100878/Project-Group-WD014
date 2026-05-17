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
}
