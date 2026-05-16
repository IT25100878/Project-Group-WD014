package com.drivingschool.service;

import com.drivingschool.model.Vehicle;
import com.drivingschool.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private FileHandler fileHandler;
    private final String FILE_NAME = "vehicles.txt";

}
