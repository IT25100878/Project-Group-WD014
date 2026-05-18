package com.drivingschool.controller;

import com.drivingschool.model.Payment;
import com.drivingschool.service.PaymentService;
import com.drivingschool.service.StudentService;
import com.drivingschool.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private PackageService packageService;

    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) throws IOException {
        List<Payment> payments;
        if (search != null && !search.trim().isEmpty()) {
            payments = paymentService.searchPayments(search);
            model.addAttribute("searchKeyword", search);
        } else {
            payments = paymentService.getAllPayments();
        }
        model.addAttribute("payments", payments);
        return "payment-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) throws IOException {
        Payment empty = new Payment("", "", "", 0.0, LocalDate.now(), "Paid");
        model.addAttribute("payment", empty);
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("packages", packageService.getAllPackages());
        return "payment-form";
    }

    @PostMapping("/save")
    public String save(Payment payment) throws IOException {
        if (payment.getId() == null || payment.getId().isEmpty()) {
            // Create new payment
            List<Payment> existing = paymentService.getAllPayments();
            int nextId = existing.size() + 1;
            String newId = "PAY" + String.format("%03d", nextId);
            payment.setId(newId);
            paymentService.addPayment(payment);
        } else {
            // Update existing payment
            paymentService.updatePayment(payment);
        }
        return "redirect:/payments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) throws IOException {
        Payment payment = paymentService.getPaymentById(id);
        model.addAttribute("payment", payment);
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("packages", packageService.getAllPackages());
        return "payment-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) throws IOException {
        paymentService.deletePayment(id);
        return "redirect:/payments";
    }
}
