package com.drivingschool.service;

import com.drivingschool.model.Payment;
import com.drivingschool.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private FileHandler fileHandler;
    private final String FILE_NAME = "payments.txt";

    public void addPayment(Payment payment) throws IOException {
        fileHandler.appendLine(FILE_NAME, payment.toFileString());
    }

    public List<Payment> getAllPayments() throws IOException {
        List<String> lines = fileHandler.readAllLines(FILE_NAME);
        List<Payment> list = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                list.add(Payment.fromFileString(line));
            }
        }
        return list;
    }

    public Payment getPaymentById(String id) throws IOException {
        return getAllPayments().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updatePayment(Payment updated) throws IOException {
        List<Payment> list = getAllPayments();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updated.getId())) {
                list.set(i, updated);
                break;
            }
        }
        saveAll(list);
    }

    public void deletePayment(String id) throws IOException {
        List<Payment> filtered = getAllPayments().stream()
                .filter(p -> !p.getId().equals(id))
                .collect(Collectors.toList());
        saveAll(filtered);
    }

    private void saveAll(List<Payment> list) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Payment p : list) {
            lines.add(p.toFileString());
        }
        fileHandler.writeAllLines(FILE_NAME, lines);
    }

    public List<Payment> searchPayments(String keyword) throws IOException {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllPayments();
        }
        String lowerKeyword = keyword.toLowerCase().trim();
        return getAllPayments().stream()
                .filter(p -> p.getId().toLowerCase().contains(lowerKeyword) ||
                        p.getStudentId().toLowerCase().contains(lowerKeyword) ||
                        p.getPackageId().toLowerCase().contains(lowerKeyword) ||
                        p.getStatus().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
}

