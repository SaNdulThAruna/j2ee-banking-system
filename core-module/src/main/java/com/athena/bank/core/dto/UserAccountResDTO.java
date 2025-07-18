package com.athena.bank.core.dto;

import java.io.Serializable;

public class UserAccountResDTO implements Serializable {

    String accountId; // Optional, can be used for internal references
    String accountName;
    String accountNumber; // Optional, can be used for display purposes
    String balance;
    String accountType;
    String accountStatus; // Optional, can be used to indicate if the account is active, inactive, etc.

    public UserAccountResDTO(String accountId, String accountName, String accountNumber, String balance, String accountType, String accountStatus) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
