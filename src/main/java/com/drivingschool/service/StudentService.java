package com.drivingschool.service;

import com.drivingschool.model.Student;
import com.drivingschool.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private FileHandler fileHandler;
    private final String FILE_NAME = "students.txt";

    //Add a new student
    public void addStudent(Student student) throws IOException {
        fileHandler.appendLine(FILE_NAME, student.toFileString());
    }

    //Read all students from the file
    public List<Student> getAllStudents() throws IOException {
        List<String> lines = fileHandler.readAllLines(FILE_NAME);
        List<Student> students = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                students.add(Student.fromFileString(line));
            }
        }
        return students;
    }

    //Find student by ID
    public Student getStudentById(String id) throws IOException {
        return getAllStudents().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    //update student list
    public void updateStudent(Student updated) throws IOException {
        List<Student> students = getAllStudents();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(updated.getId())) {
                students.set(i, updated);
                break;
            }
        }
        saveAll(students);
    }

    //delete existing student
    public void deleteStudent(String id) throws IOException {
        List<Student> filtered = getAllStudents().stream()
                .filter(s -> !s.getId().equals(id))
                .collect(Collectors.toList());
        saveAll(filtered);
    }

    private void saveAll(List<Student> students) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Student s : students) {
            lines.add(s.toFileString());
        }
        fileHandler.writeAllLines(FILE_NAME, lines);
    }

    //Search student by keywords
    public List<Student> searchStudents(String keyword) throws IOException {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents();
        }
        String lowerKeyword = keyword.toLowerCase().trim();
        return getAllStudents().stream()
                .filter(s -> s.getId().toLowerCase().contains(lowerKeyword) ||
                        s.getName().toLowerCase().contains(lowerKeyword) ||
                        s.getNic().toLowerCase().contains(lowerKeyword) ||
                        s.getPhone().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
}

