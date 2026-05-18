package com.drivingschool.controller;

import com.drivingschool.model.Package;
import com.drivingschool.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/packages")
public class PackageController {
    @Autowired private PackageService packageService;

    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) throws IOException {
        List<Package> packages;
        if (search != null && !search.trim().isEmpty()) {
            packages = packageService.searchPackages(search);
            model.addAttribute("searchKeyword", search);
        } else {
            packages = packageService.getAllPackages();
        }
        model.addAttribute("packages", packages);
        return "package-list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("pkg", new Package("", "", 0, 0.0, ""));
        return "package-form";
    }

    @PostMapping("/save")
    public String save(Package pkg) throws IOException {
        if (pkg.getId() == null || pkg.getId().isEmpty()) {
            List<Package> existing = packageService.getAllPackages();
            int nextId = existing.size() + 1;
            pkg.setId("PKG" + String.format("%03d", nextId));
            packageService.addPackage(pkg);
        } else {
            packageService.updatePackage(pkg);
        }
        return "redirect:/packages";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) throws IOException {
        model.addAttribute("pkg", packageService.getPackageById(id));
        return "package-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) throws IOException {
        packageService.deletePackage(id);
        return "redirect:/packages";
    }
}
