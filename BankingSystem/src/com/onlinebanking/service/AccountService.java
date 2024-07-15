package com.onlinebanking.service;

import com.onlinebanking.DatabaseConnection;
import com.onlinebanking.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountService {

    public void createAccount(Account account) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO accounts (user_id, type, balance) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, account.getUserId());
            preparedStatement.setString(2, account.getType());
            preparedStatement.setDouble(3, account.getBalance());
            preparedStatement.executeUpdate();
            System.out.println("Account created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account getAccountById(int accountId) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM accounts WHERE id = ?";
        Account account = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setUserId(resultSet.getInt("user_id"));
                account.setType(resultSet.getString("type"));
                account.setBalance(resultSet.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public void updateAccountBalance(int accountId, double newBalance) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "UPDATE accounts SET balance = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, accountId);
            preparedStatement.executeUpdate();
            System.out.println("Account balance updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
