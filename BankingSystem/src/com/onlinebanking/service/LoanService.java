package com.onlinebanking.service;

import com.onlinebanking.DatabaseConnection;
import com.onlinebanking.model.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanService {

    public void applyForLoan(Loan loan) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO loans (user_id, amount, interest_rate, duration, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, loan.getUserId());
            preparedStatement.setDouble(2, loan.getAmount());
            preparedStatement.setDouble(3, loan.getInterestRate());
            preparedStatement.setInt(4, loan.getDuration());
            preparedStatement.setString(5, loan.getStatus());
            preparedStatement.executeUpdate();
            System.out.println("Loan application submitted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Loan> getLoansByUserId(int userId) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM loans WHERE user_id = ?";
        List<Loan> loans = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Loan loan = new Loan();
                loan.setId(resultSet.getInt("id"));
                loan.setUserId(resultSet.getInt("user_id"));
                loan.setAmount(resultSet.getDouble("amount"));
                loan.setInterestRate(resultSet.getDouble("interest_rate"));
                loan.setDuration(resultSet.getInt("duration"));
                loan.setStatus(resultSet.getString("status"));
                loans.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    public void updateLoanStatus(int loanId, String status) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "UPDATE loans SET status = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, loanId);
            preparedStatement.executeUpdate();
            System.out.println("Loan status updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
