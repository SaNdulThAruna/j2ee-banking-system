package com.athena.bank.core.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "interest_logs")
@NamedQueries({
        @NamedQuery(name = "InterestLog.findDalyLog", query = "SELECT il FROM InterestLog il WHERE FUNCTION('DATE', il.appliedDate) = :appliedDate"),
})
public class InterestLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "interest_amount", nullable = false)
    private Double interestAmount;

    @Column(name = "rate_applied", nullable = false)
    private Double rateApplied;

    @Column(name = "applied_date", nullable = false)
    private LocalDateTime appliedDate;

    public InterestLog() {
    }

    public InterestLog(Account account, Double interestAmount, Double rateApplied) {
        this.account = account;
        this.interestAmount = interestAmount;
        this.rateApplied = rateApplied;
        this.appliedDate = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Double interestAmount) {
        this.interestAmount = interestAmount;
    }

    public Double getRateApplied() {
        return rateApplied;
    }

    public void setRateApplied(Double rateApplied) {
        this.rateApplied = rateApplied;
    }

    public LocalDateTime getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDateTime appliedDate) {
        this.appliedDate = appliedDate;
    }
}
