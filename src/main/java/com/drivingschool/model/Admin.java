package com.drivingschool.model;

public class Admin {
    private String id;
    private String username;
    private String password;
    private String role; // SuperAdmin, Admin

    public Admin(String id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters (generate via IntelliJ)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //converts object data into a single string.
    public String toFileString() {
        return id + "|" + username + "|" + password + "|" + role;

    }
    //converts a text line from the file into an admin object.
    public static Admin fromFileString(String line) {

        String[] parts = line.split("\\|");  //split the line using "|"

        return new Admin(parts[0], parts[1], parts[2], parts[3]); //create and return a new Admin object

    }


}
