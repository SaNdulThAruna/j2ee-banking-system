package com.athena.bank.ejb.scheduler;

import com.athena.bank.core.model.Account;
import com.athena.bank.core.model.AccountStatus;
import com.athena.bank.core.model.AccountType;
import com.athena.bank.core.model.InterestLog;
import com.athena.bank.ejb.interceptor.LoggingInterceptor;
import jakarta.ejb.*;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Singleton
@Startup
@Interceptors({LoggingInterceptor.class}) // cross-cutting logging
public class InterestSchedulerBean {

    @PersistenceContext
    private EntityManager em;

    private static final double DAILY_INTEREST_RATE = 0.0001; //0.01% daily

    // Run every day at midnight
    @Schedule(hour = "0", minute = "0", second = "0", persistent = false)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void applyDailyInterest() {

        try {
            System.out.println("[Scheduler] Starting daily interest calculation...");

            List<Account> eligibleAccounts = getEligibleAccounts();
            for (Account acc : eligibleAccounts) {
                double interest = acc.getBalance() * DAILY_INTEREST_RATE;
                acc.setBalance(acc.getBalance() + interest);
                em.merge(acc); // Update the account with new balance
                em.persist(new InterestLog(acc, interest, DAILY_INTEREST_RATE)); // Log the interest applied
            }

            System.out.println("[Scheduler] Interest applied to " + eligibleAccounts.size() + " accounts.");

        } catch (Exception e) {
            System.err.println("[Scheduler] Failed to apply interest: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private List<Account> getEligibleAccounts() {
        String jpql = "SELECT a FROM Account a WHERE a.accountType = :type AND a.status = :status";
        TypedQuery<Account> query = em.createQuery(jpql, Account.class);
        query.setParameter("type", AccountType.SAVINGS);
        query.setParameter("status", AccountStatus.ACTIVE);
        return query.getResultList();
    }

}
