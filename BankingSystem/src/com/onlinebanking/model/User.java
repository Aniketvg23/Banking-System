package com.onlinebanking.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String pin;

    public User() {}

    public User(int id, String username, String password, String pin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.pin = pin;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
