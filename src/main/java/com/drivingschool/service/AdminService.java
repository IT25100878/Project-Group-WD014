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




}
