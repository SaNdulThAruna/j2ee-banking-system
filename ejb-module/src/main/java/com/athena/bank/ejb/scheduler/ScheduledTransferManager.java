package com.athena.bank.ejb.scheduler;

import com.athena.bank.core.model.Account;
import com.athena.bank.core.model.Transaction;
import com.athena.bank.core.model.TransactionStatus;
import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Singleton
public class ScheduledTransferManager {

    @Resource
    private TimerService timerService;

    @PersistenceContext
    private EntityManager em;


    public void scheduleTransferTimer(Transaction txn) {
        TimerConfig config = new TimerConfig();
        config.setInfo(txn.getId()); // store transaction ID
        config.setPersistent(true);

        Date executionDate = Date.from(txn.getTransactionTime().atZone(ZoneId.systemDefault()).toInstant());
        Date now = new Date();
        System.out.println("🕒 Now: " + now);
        System.out.println("🕒 Scheduling at: " + executionDate);

        if (executionDate.before(now)) {
            System.err.println("❌ Skipping: execution time is in the past.");
            return;
        }
        System.out.println("🕒 Scheduling transfer at: " + executionDate);
        timerService.createSingleActionTimer(executionDate, config);
        System.out.println("✅ Timer scheduled for txn #" + txn.getId());
    }

    @Timeout
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void executeTransfer(Timer timer) {
        System.out.println("🔥 Timer triggered at: " + LocalDateTime.now());
        int txnId = (int) timer.getInfo();
        Transaction txn = em.find(Transaction.class, txnId);
        if (txn == null || txn.getStatus() != TransactionStatus.PENDING) return;

        try {
            Account from = em.find(Account.class, txn.getFromAccount().getId());
            Account to = em.find(Account.class, txn.getToAccount().getId());

            if (from.getBalance() < txn.getAmount()) {
                txn.setStatus(TransactionStatus.FAILED);
                txn.setDescription("Insufficient funds");
            } else {
                from.setBalance(from.getBalance() - txn.getAmount());
                to.setBalance(to.getBalance() + txn.getAmount());

                em.merge(from);
                em.merge(to);
                txn.setStatus(TransactionStatus.SUCCESS);
            }

            em.merge(txn);

            System.out.println("✅ Transaction updated successfully: " + txnId);
        } catch (Exception e) {
            txn.setStatus(TransactionStatus.FAILED);
            txn.setDescription("Execution failed: " + e.getMessage());
            em.merge(txn);
            System.err.println("❌ Transaction failed: " + txnId);
            e.printStackTrace();
        }
    }

}
