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
    public String showCreateForm(Model model) {
        //Send empty admin object to form
        model.addAttribute("admin", new Admin("", "", "", ""));
        return "admin-form"; //return admin form page
    }
    //save new admin or update existing admin
    @PostMapping("/save")
    public String save(Admin admin) throws IOException {
        //check if admin is new
        if (admin.getId() == null || admin.getId().isEmpty()) {

            //CREATE – generate unique ID
            List<Admin> existing = adminService.getAllAdmins(); //Get all existing admins
            int maxId = 0; //Store highest admin number

            for (Admin a : existing) { //check each admin ID
                String id = a.getId(); //get admin ID

                if (id != null && id.startsWith("ADM")) { //check valid ID format

                    try {
                        int num = Integer.parseInt(id.substring(3)); //extract number part from ID
                        if (num > maxId) maxId = num; //update highest ID number

                    } catch (NumberFormatException ignored) {}
                }
            }

            int nextId = maxId + 1; //generate next admin ID
            String newId = "ADM" + String.format("%03d", nextId); //format ID
            admin.setId(newId); //set new admin ID
            adminService.addAdmin(admin); //save new admin

        } else {

            //update existing admin
            adminService.updateAdmin(admin);
        }
        //redirect to admin list page
        return "redirect:/admins";
    }

    //gets admin data by ID and opens the form page for editing
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) throws IOException {
        //get admin by ID and send data to form
        model.addAttribute("admin", adminService.getAdminById(id));
        return "admin-form"; //return admin form page
    }

    //delete admin by ID and redirects to the admin list page
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) throws IOException {
        //remove admin from service using ID
        adminService.deleteAdmin(id);
        return "redirect:/admins";
    }



}

