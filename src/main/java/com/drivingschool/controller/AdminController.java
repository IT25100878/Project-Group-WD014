package com.drivingschool.controller;

import com.drivingschool.model.Admin;
import com.drivingschool.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping
    public String list(Model model) throws IOException {
        model.addAttribute("admins", adminService.getAllAdmins());
        return "admin-list";
    }
    //Open form page for adding a new admin
    @GetMapping("/new")
    public String createForm(Model model) {
        //Send empty admin object to form
        model.addAttribute("admin", new Admin("", "", "", ""));
        return "admin-form"; //return admin form page
    }


}
