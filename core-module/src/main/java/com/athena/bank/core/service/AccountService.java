package com.athena.bank.core.service;

import com.athena.bank.core.dto.*;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface AccountService {

    boolean createAccount(AccountRequest accountRequest); // Create a new account

    boolean createUserAndAccount(UserRegisterDTO userRegisterDTO);

    AccountDTO getAccountById(int id);

    AccountDTO getAccountByAccountNumber(String accountNumber);

    List<AccountDTO> getAccountsByCustomerId(int customerId);

    List<AccountDTO> getActiveAccountsByCustomerId(int customerId);

    boolean closeAccount(String accountNumber);

    boolean deleteAccount(int accountId); // Delete an account (admin use)

    int getTotalTransactions();

    int getTotalAccounts();

    List<AccountTableResDTO> filterAccounts(String searchText, String accountType, String accountStatus); // Filter accounts based on search criteria

    List<InterestResDTO> getDalyInterestLog(); // Get all interest transactions

    List<InterestResDTO> getAllInterestLog(); // Get all interest transactions (admin use)

    List<TransactionHistoryResDTO> getDailyTransactionHistory(); // Get daily transaction history

    List<UserTransactionResDTO> getAllTransactionList(int customerId);

    boolean deposit(int accountId, double amount);           // Deposit money

    boolean transfer(FundTransferRequest fundTransferRequest); // Transfer funds

    boolean scheduleTransfer(ScheduleTransferRequestDTO scheduleTransferRequestDTO); // Schedule a transfer

    List<TransactionHistoryResDTO> getTransactionHistoryByCustomer(int customerId, String date); // Get transaction

    List<TransactionHistoryResDTO> getTransactionHistory(TransactionHistoryReqDTO transactionHistoryReqDTO); // Get
    // transaction history for an account

    double getBalance(int accountId);                        // Get current balance

    double getAllBalance(int customerId);                        // Get total balance across all accounts for a customer

    boolean isAccountActive(int accountId);                  // Check account status

    List<AccountDTO> getAllAccounts();                       // List all accounts (admin use)

}
