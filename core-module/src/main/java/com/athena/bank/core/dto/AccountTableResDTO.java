package com.athena.bank.core.dto;

import java.io.Serializable;

public class AccountTableResDTO implements Serializable {
    private int id;
    private String fullName;
    private String email;
    private String username;
    private String accountName;
    private String accountType;
    private double balance;
    private String status;

    public AccountTableResDTO(int id, String fullName, String email, String username, String accountName, String accountType, double balance, String status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}