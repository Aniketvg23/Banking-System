package com.onlinebanking.service;

import com.onlinebanking.DatabaseConnection;
import com.onlinebanking.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    public void registerUser(User user) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO users (username, password, pin) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());  // Ensure to hash the password
            preparedStatement.setString(3, user.getPin());        // Ensure to hash the pin
            preparedStatement.executeUpdate();
            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User authenticateUser(String username, String password) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        User user = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);  // Ensure to hash and compare the password
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setPin(resultSet.getString("pin"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
