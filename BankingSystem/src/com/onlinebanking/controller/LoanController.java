package com.onlinebanking.controller;

import com.onlinebanking.model.Loan;
import com.onlinebanking.service.LoanService;

import java.util.List;

public class LoanController {
    private LoanService loanService = new LoanService();

    public void applyForLoan(int userId, double amount, double interestRate, int duration, String status) {
        Loan loan = new Loan();
        loan.setUserId(userId);
        loan.setAmount(amount);
        loan.setInterestRate(interestRate);
        loan.setDuration(duration);
        loan.setStatus(status);
        loanService.applyForLoan(loan);
    }

    public List<Loan> getUserLoans(int userId) {
        return loanService.getLoansByUserId(userId);
    }

    public void updateLoanStatus(int loanId, String status) {
        loanService.updateLoanStatus(loanId, status);
    }
}
