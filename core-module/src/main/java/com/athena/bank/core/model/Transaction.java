package com.athena.bank.core.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "Transaction.findById", query = "SELECT t FROM Transaction t WHERE t.id = :id"),
        @NamedQuery(name = "Transaction.findByCustomerId", query = "SELECT t FROM Transaction t WHERE t.fromAccount.customer.id = :customerId OR t.toAccount.customer.id = :customerId ORDER BY t.transactionTime DESC"),
        @NamedQuery(name = "Transaction.findByAccountNumberAndDate", query = "SELECT t FROM Transaction t WHERE t.fromAccount.accountNumber = :accountId OR t.toAccount.accountNumber = :accountId and t.transactionTime = :transactionTime ORDER BY t.transactionTime DESC"),
})
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(name = "transaction_time", nullable = false)
    private LocalDateTime transactionTime;

    private String description;

    public Transaction() {
    }

    public Transaction(Account fromAccount, Account toAccount, Double amount, TransactionType type,
                       TransactionStatus status, LocalDateTime transactionTime, String description) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.transactionTime = transactionTime;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
