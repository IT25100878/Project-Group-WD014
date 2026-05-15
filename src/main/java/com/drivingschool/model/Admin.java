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


}
