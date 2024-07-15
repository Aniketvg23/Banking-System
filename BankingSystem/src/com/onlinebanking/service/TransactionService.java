package com.onlinebanking.service;

import com.onlinebanking.model.Account;
import com.onlinebanking.model.Transaction;
import com.onlinebanking.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    public void createTransaction(Transaction transaction) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO transactions (account_id, type, amount) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, transaction.getAccountId());
            preparedStatement.setString(2, transaction.getType());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.executeUpdate();
            System.out.println("Transaction created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM transactions WHERE account_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getInt("id"));
                transaction.setAccountId(resultSet.getInt("account_id"));
                transaction.setType(resultSet.getString("type"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTimestamp(resultSet.getTimestamp("timestamp"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void transferFunds(int fromAccountId, int toAccountId, double amount) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            // Begin transaction
            connection.setAutoCommit(false);

            // Check if both accounts exist
            Account fromAccount = getAccountById(fromAccountId);
            Account toAccount = getAccountById(toAccountId);
            if (fromAccount == null || toAccount == null) {
                throw new SQLException("One or both accounts do not exist.");
            }

            // Deduct amount from sender's account
            updateAccountBalance(fromAccountId, -amount);

            // Add amount to receiver's account
            updateAccountBalance(toAccountId, amount);

            // Log transaction for sender
            createTransaction(new Transaction(0, fromAccountId, "DEBIT", amount, null));

            // Log transaction for receiver
            createTransaction(new Transaction(0, toAccountId, "CREDIT", amount, null));

            // Commit transaction
            connection.commit();
            System.out.println("Funds transferred successfully.");

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                autoCommitEx.printStackTrace();
            }
        }
    }

    private void updateAccountBalance(int accountId, double amount) throws SQLException {
        AccountService accountService = new AccountService();
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            throw new SQLException("Account not found for ID: " + accountId);
        }
        double newBalance = account.getBalance() + amount;
        accountService.updateAccountBalance(accountId, newBalance);
    }

    private Account getAccountById(int accountId) {
        AccountService accountService = new AccountService();
        return accountService.getAccountById(accountId);
    }
}
