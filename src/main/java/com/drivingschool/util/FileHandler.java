package com.drivingschool.util;

import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandler {
    private final String BASE_PATH = "src/main/resources/data/";

    //Rewrite all lines
    public void writeAllLines(String fileName, List<String> lines) throws IOException {
        Path path = Paths.get(BASE_PATH + fileName);
        Files.write(path, lines);
    }

    //Get all lines
    public List<String> readAllLines(String fileName) throws IOException {
        Path path = Paths.get(BASE_PATH + fileName);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        return Files.readAllLines(path);
    }

    //Add a new line in text document
    public void appendLine(String fileName, String line) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BASE_PATH + fileName, true))) {
            writer.write(line);
            writer.newLine();
        }
    }
}
