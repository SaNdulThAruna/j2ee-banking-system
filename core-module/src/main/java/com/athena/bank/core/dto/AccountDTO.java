package com.athena.bank.core.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AccountDTO implements Serializable {

    private int id;
    private String accountNumber;
    private String accountName; // Optional, can be used for display purposes
    private double balance;
    private String accountType;     // e.g., SAVINGS, CHECKING
    private String status;          // e.g., ACTIVE, CLOSED, SUSPENDED
    private int customerId;         // FK to customer
    private LocalDateTime createdAt;

    private String customerName;

    public AccountDTO(int id, String accountNumber, String accountName, double balance, String accountType, String status, int customerId, LocalDateTime createdAt, String customerName) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
        this.accountType = accountType;
        this.status = status;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.customerName = customerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
