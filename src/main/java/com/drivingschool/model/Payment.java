package com.drivingschool.model;

import java.time.LocalDate;

public class Payment {
    private String id;
    private String studentId;
    private String packageId;
    private double amount;
    private LocalDate paymentDate;
    private String status; // Paid, Pending, Refunded

    public Payment(String id, String studentId, String packageId, double amount, LocalDate paymentDate, String status) {
        this.id = id;
        this.studentId = studentId;
        this.packageId = packageId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    // Getters and Setters (generate via IntelliJ: right-click → Generate → Getter and Setter)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getPackageId() { return packageId; }
    public void setPackageId(String packageId) { this.packageId = packageId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toFileString() {
        return id + "|" + studentId + "|" + packageId + "|" + amount + "|" + paymentDate + "|" + status;
    }

    public static Payment fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Payment(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]),
                LocalDate.parse(parts[4]), parts[5]);
    }
}

