package com.drivingschool.controller;
import com.drivingschool.model.Instructor;
import com.drivingschool.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) throws IOException {
        List<Instructor> instructors;
        if (search != null && !search.trim().isEmpty()) {
            instructors = instructorService.searchInstructors(search);
            model.addAttribute("searchKeyword", search);
        } else {
            instructors = instructorService.getAllInstructors();
        }
        model.addAttribute("instructors", instructors);
        return "instructor-list";
    }
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("instructor", new Instructor("", "", "", "", "", ""));
        return "instructor-form";
    }
    @PostMapping("/save")
    public String save(Instructor instructor) throws IOException {
        if (instructor.getId() == null || instructor.getId().isEmpty()) {
            List<Instructor> existing = instructorService.getAllInstructors();
            int nextId = existing.size() + 1;
            instructor.setId("INS" + String.format("%03d", nextId));
            instructor.setStatus("Available");
            instructorService.addInstructor(instructor);
        } else {
            instructorService.updateInstructor(instructor);
        }
        return "redirect:/instructors";
    }
}
