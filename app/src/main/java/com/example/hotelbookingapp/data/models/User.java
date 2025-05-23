package com.example.hotelbookingapp.data.models;

public class User {
    private int id;
    private final String email;
    private final String password;
    private final String role;

    public User(int id, String name, String email, String password, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String name, String email, String password) {
        this.email = email;
        this.password = password;
        this.role = "user";
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = "user";
    }

    // Getters and setters (bạn có thể generate từ IDE)
    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}
