package com.onlinebanking;

import com.onlinebanking.controller.*;
import com.onlinebanking.model.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.initialize();

        UserController userController = new UserController();
        AccountController accountController = new AccountController();
        TransactionController transactionController = new TransactionController();
        LoanController loanController = new LoanController();

        Scanner scanner = new Scanner(System.in);

        // Register User
        System.out.print("Enter username for registration: ");
        String username = scanner.nextLine();
        System.out.print("Enter password for registration: ");
        String password = scanner.nextLine();
        System.out.print("Enter PIN for registration: ");
        String pin = scanner.nextLine();
        userController.registerUser(username, password, pin);

        // Login User
        System.out.print("Enter username for login: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter password for login: ");
        String loginPassword = scanner.nextLine();
        User user = userController.loginUser(loginUsername, loginPassword);

        if (user != null) {
            System.out.println("User logged in successfully.");

            // Create Account
            accountController.createAccount(user.getId(), "checking", 5000);
            System.out.println("Account created successfully.");
            System.out.println("Account Details: " + accountController.getAccount(user.getId()));

            // Assume we create another account manually for testing
            accountController.createAccount(user.getId(), "savings", 3000);

            // Get account IDs (assuming IDs are 1 and 2 for testing purposes)
            int fromAccountId = 1;
            int toAccountId = 2;

            // Transfer Funds
            transactionController.transferFunds(fromAccountId, toAccountId, 500);

            // Apply for Loan
            loanController.applyForLoan(user.getId(), 10000, 5, 12, "pending");
            System.out.println("Loan application submitted successfully.");

        } else {
            System.out.println("Invalid login credentials.");
        }
        scanner.close();
    }
}
