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

    //find one admin using ID
    public Admin getAdminById(String id) throws IOException {

        return getAllAdmins().stream() //get all admins and search for the matching ID
                .filter(a -> a.getId().equals(id)) //check if admin ID matches the given ID
                .findFirst()  //get first matching admin
                .orElse(null);  //return null if no admin is found
    }

    //update admin details in the file
    public void updateAdmin(Admin updated) throws IOException {
        List<Admin> list = getAllAdmins(); //get all admins
        for (int i = 0; i < list.size(); i++) {  //loop through the list
            if (list.get(i).getId().equals(updated.getId()))  //check if admin ID matches

                list.set(i, updated); //replace old admin with new details
        }
        saveAll(list); //save updated list back to the file
    }

    //method to delete an admin by ID
    public void deleteAdmin(String id) throws IOException {

        List<Admin> filtered = getAllAdmins() // get all admins and remove the one with matching ID
                .stream()
                .filter(a -> !a.getId().equals(id)) //keep admins whose ID is not equal to given ID
                .collect(Collectors.toList()); //convert result back to list

        saveAll(filtered);//save updated list back to the file
    }

    //save all admin data to the file
    private void saveAll(List<Admin> list) throws IOException {

        List<String> lines = new ArrayList<>(); //create a list to store admin data as string
        for (Admin a : list)  //loop through admins
            lines.add(a.toFileString()); //convert each admin into text and add to list

        fileHandler.writeAllLines(FILE_NAME, lines); //write all lines to file
    }





}
