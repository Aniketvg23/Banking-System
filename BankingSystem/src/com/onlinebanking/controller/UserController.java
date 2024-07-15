package com.onlinebanking.controller;

import com.onlinebanking.model.User;
import com.onlinebanking.service.UserService;

public class UserController {
    private UserService userService = new UserService();

    public void registerUser(String username, String password, String pin) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);  // Ensure to hash the password
        user.setPin(pin);            // Ensure to hash the pin
        userService.registerUser(user);
    }

    public User loginUser(String username, String password) {
        return userService.authenticateUser(username, password);  // Ensure to hash and compare the password
    }
}
