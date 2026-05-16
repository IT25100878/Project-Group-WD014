package com.drivingschool.controller;

import com.drivingschool.model.Student;
import com.drivingschool.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String listStudents(@RequestParam(required = false) String search, Model model) throws IOException {
        List<Student> students;
        if (search != null && !search.trim().isEmpty()) {
            students = studentService.searchStudents(search);
            model.addAttribute("searchKeyword", search);
        } else {
            students = studentService.getAllStudents();
        }
        model.addAttribute("students", students);
        return "student-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student("", "", "", "", "", "", "", ""));
        return "student-form";
    }

    @PostMapping("/save")
    public String saveStudent(Student student) throws IOException {
        if (student.getId() == null || student.getId().isEmpty()) {
            //generate unique ID
            List<Student> existing = studentService.getAllStudents();
            int maxId = 0;
            for (Student s : existing) {
                String id = s.getId();
                if (id != null && id.startsWith("STU")) {
                    try {
                        int num = Integer.parseInt(id.substring(3));
                        if (num > maxId) maxId = num;
                    } catch (NumberFormatException ignored) {}
                }
            }
            int nextId = maxId + 1;
            String newId = "STU" + String.format("%03d", nextId);
            student.setId(newId);
            student.setStatus("Active");
            if (student.getPassword() == null || student.getPassword().isEmpty()) {
                student.setPassword("123456");
            }
            studentService.addStudent(student);
        } else {
            // UPDATE
            studentService.updateStudent(student);
        }
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) throws IOException {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "student-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id) throws IOException {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
