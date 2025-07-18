package com.athena.bank.core.dto;

import java.io.Serializable;

public class TransactionHistoryReqDTO implements Serializable {

    String accountNumber;
    String date;

    public TransactionHistoryReqDTO(String accountNumber, String date) {
        this.accountNumber = accountNumber;
        this.date = date;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
