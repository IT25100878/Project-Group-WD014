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
        Student emptyStudent = new Student("", "", "", "", "", "", "", "");
        model.addAttribute("student", emptyStudent);
        return "student-form";
    }

    @PostMapping("/save")


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) throws IOException {
        Student student = studentService.getStudentById(id);   // ✅ fixed
        model.addAttribute("student", student);               // ✅ fixed
        return "student-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id) throws IOException {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
