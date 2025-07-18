package com.athena.bank.core.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "Account.findByCustomerId", query = "SELECT a FROM Account a WHERE a.customer.id =" +
                        " :customerId"),
                @NamedQuery(name = "Account.findByCustomerIdAndStatus", query = "SELECT a FROM Account a WHERE a.customer.id =" +
                        " :customerId AND a.status = :status"),
                @NamedQuery(name = "Account.findByAccountNumber", query = "SELECT a FROM Account a WHERE a" +
                        ".accountNumber = :accountNumber"),
                @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
        }
)
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false, unique = true, name = "account_number")
    private String accountNumber;

    private String accountName; // Optional field for account name

    @Column(nullable = false)
    private Double balance = 0.00;
    @Enumerated(EnumType.STRING)
    private AccountType accountType = AccountType.SAVINGS; // Default to SAVINGS
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;
    private LocalDateTime createdAt;

    public Account() {
    }

    public Account(Customer customer,String accountName, String accountNumber, AccountType accountType) {
        this.customer = customer;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
