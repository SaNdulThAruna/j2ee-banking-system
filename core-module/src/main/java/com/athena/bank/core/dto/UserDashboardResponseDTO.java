package com.athena.bank.core.dto;

import java.io.Serializable;
import java.util.List;

public class UserDashboardResponseDTO implements Serializable {

    String totalBalance;
    List<UserAccountResDTO> accountsList;
    List<UserTransactionResDTO> transactionList;

    public UserDashboardResponseDTO(String totalBalance, List<UserAccountResDTO> accountsList, List<UserTransactionResDTO> transactionList) {
        this.totalBalance = totalBalance;
        this.accountsList = accountsList;
        this.transactionList = transactionList;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public List<UserAccountResDTO> getAccountsList() {
        return accountsList;
    }

    public void setAccountsList(List<UserAccountResDTO> accountsList) {
        this.accountsList = accountsList;
    }

    public List<UserTransactionResDTO> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<UserTransactionResDTO> transactionList) {
        this.transactionList = transactionList;
    }
}
