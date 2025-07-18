package com.athena.bank.core.dto;

import java.io.Serializable;

public class InterestResDTO implements Serializable {

    String accountNumber;
    String interestRate;
    String appliedDate;

    public InterestResDTO(String accountNumber, String interestRate, String appliedDate) {
        this.accountNumber = accountNumber;
        this.interestRate = interestRate;
        this.appliedDate = appliedDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }
}
