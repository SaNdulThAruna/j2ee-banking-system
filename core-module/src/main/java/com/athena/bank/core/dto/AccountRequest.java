package com.athena.bank.core.dto;

import java.io.Serializable;

public class AccountRequest implements Serializable {

    private int customerId;
    private String accountName;
    private String accountType;

    public AccountRequest(int customerId,String accountName, String accountType) {
        this.customerId = customerId;
        this.accountName = accountName;
        this.accountType = accountType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
}
