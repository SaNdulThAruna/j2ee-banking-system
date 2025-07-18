package com.athena.bank.core.dto;

import java.io.Serializable;

public class ScheduleTransferRequestDTO implements Serializable {

    private int fromAccountId;
    private String fromAccountNumber;
    private String toAccountNumber;
    private double amount;
    private String transferDate;
    private String description;

    public ScheduleTransferRequestDTO(int fromAccountId, String toAccountNumber, double amount, String transferDate, String description) {
        this.fromAccountId = fromAccountId;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.transferDate = transferDate;
        this.description = description;
    }

    public ScheduleTransferRequestDTO(String fromAccountNumber, String toAccountNumber, int fromAccountId, double amount, String transferDate, String description) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.fromAccountId = fromAccountId;
        this.amount = amount;
        this.transferDate = transferDate;
        this.description = description;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
