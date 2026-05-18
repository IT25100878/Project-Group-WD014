package com.drivingschool.controller;

import com.drivingschool.model.Student;
import com.drivingschool.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/student")
public class StudentLoginController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("error", null);
        return "student-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) throws IOException {
        for (Student student : studentService.getAllStudents()) {
            if ((student.getId().equals(username) || student.getNic().equals(username))
                    && student.getPassword().equals(password)) {
                session.setAttribute("loggedStudent", student);
                return "redirect:/student/dashboard";
            }
        }
        model.addAttribute("error", "Invalid Student ID/NIC or Password");
        return "student-login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/student/login";
    }
}
