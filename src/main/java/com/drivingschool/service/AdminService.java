package com.drivingschool.service;

import com.drivingschool.model.Admin;
import com.drivingschool.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdminService {
    @Autowired private FileHandler fileHandler;
    private final String FILE_NAME = "admins.txt";

    public void addAdmin(Admin admin) throws IOException {
        fileHandler.appendLine(FILE_NAME, admin.toFileString());
    }

    //reads all admin details from the file & return them as a list of Admin objects.
    public List<Admin> getAllAdmins() throws IOException {
        List<String> lines = fileHandler.readAllLines(FILE_NAME); //read all lines from the admin file
        List<Admin> list = new ArrayList<>(); //create an empty admin list

        for (String line : lines) {
            if (!line.trim().isEmpty()) //check if the line is not empty
                list.add(Admin.fromFileString(line)); //convert line to admin object and add to list
        }
        return list; //return the admin list
    }




}
