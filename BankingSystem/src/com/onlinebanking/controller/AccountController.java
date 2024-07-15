package com.onlinebanking.controller;

import com.onlinebanking.model.Account;
import com.onlinebanking.service.AccountService;

public class AccountController {
    private AccountService accountService = new AccountService();

    public void createAccount(int userId, String type, double balance) {
        Account account = new Account();
        account.setUserId(userId);
        account.setType(type);
        account.setBalance(balance);
        accountService.createAccount(account);
    }

    public Account getAccount(int accountId) {
        return accountService.getAccountById(accountId);
    }

    public void updateBalance(int accountId, double newBalance) {
        accountService.updateAccountBalance(accountId, newBalance);
    }
}
