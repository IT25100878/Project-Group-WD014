package com.drivingschool.controller;

import com.drivingschool.model.Payment;
import com.drivingschool.model.Schedule;
import com.drivingschool.model.Student;
import com.drivingschool.service.PaymentService;
import com.drivingschool.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentDashboardController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) throws IOException {
        Student student = (Student) session.getAttribute("loggedStudent");
        if (student == null) {
            return "redirect:/student/login";
        }
        model.addAttribute("student", student);

        List<Schedule> mySchedules = scheduleService.getAllSchedules().stream()
                .filter(s -> s.getStudentId().equals(student.getId()))
                .collect(Collectors.toList());
        model.addAttribute("schedules", mySchedules);

        List<Payment> myPayments = paymentService.getAllPayments().stream()
                .filter(p -> p.getStudentId().equals(student.getId()))
                .collect(Collectors.toList());
        model.addAttribute("payments", myPayments);

        return "student-dashboard";
    }
}
