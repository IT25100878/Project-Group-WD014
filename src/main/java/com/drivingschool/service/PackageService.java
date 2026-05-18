package com.drivingschool.service;

import com.drivingschool.model.Package;
import com.drivingschool.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageService {
    @Autowired private FileHandler fileHandler;
    private final String FILE_NAME = "packages.txt";

    public void addPackage(Package pkg) throws IOException {
        fileHandler.appendLine(FILE_NAME, pkg.toFileString());
    }

    public List<Package> getAllPackages() throws IOException {
        List<String> lines = fileHandler.readAllLines(FILE_NAME);
        List<Package> list = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Package pkg = Package.fromFileString(line);
                if (pkg != null) {
                    list.add(pkg);
                }
            }
        }
        return list;
    }

    public Package getPackageById(String id) throws IOException {
        return getAllPackages().stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public void updatePackage(Package updated) throws IOException {
        List<Package> list = getAllPackages();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updated.getId())) list.set(i, updated);
        }
        saveAll(list);
    }

    public void deletePackage(String id) throws IOException {
        List<Package> filtered = getAllPackages().stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList());
        saveAll(filtered);
    }

    private void saveAll(List<Package> list) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Package p : list) lines.add(p.toFileString());
        fileHandler.writeAllLines(FILE_NAME, lines);
    }

    public List<Package> searchPackages(String keyword) throws IOException {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllPackages();
        }
        String lowerKeyword = keyword.toLowerCase().trim();
        return getAllPackages().stream()
                .filter(p -> p.getId().toLowerCase().contains(lowerKeyword) ||
                        p.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
}

