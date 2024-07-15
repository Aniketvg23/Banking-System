package com.onlinebanking.controller;

import com.onlinebanking.model.Transaction;
import com.onlinebanking.service.TransactionService;

import java.util.List;

public class TransactionController {
    private TransactionService transactionService = new TransactionService();

    public void createTransaction(int accountId, String type, double amount) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setType(type);
        transaction.setAmount(amount);
        transactionService.createTransaction(transaction);
    }

    public List<Transaction> getTransactions(int accountId) {
        return transactionService.getTransactionsByAccountId(accountId);
    }

    public void transferFunds(int fromAccountId, int toAccountId, double amount) {
        transactionService.transferFunds(fromAccountId, toAccountId, amount);
    }
}
