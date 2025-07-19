package com.athena.bank.ejb.bean;

import com.athena.bank.core.dto.*;
import com.athena.bank.core.exception.BusinessException;
import com.athena.bank.core.exception.InsufficientFundException;
import com.athena.bank.core.model.*;
import com.athena.bank.core.service.AccountService;
import com.athena.bank.ejb.scheduler.ScheduledTransferManager;
import com.athena.bank.ejb.util.AccountNumberGenerator;
import com.athena.bank.ejb.util.PasswordUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class AccountBean implements AccountService {

    private static final int MAX_ATTEMPTS = 10;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private ScheduledTransferManager scheduledTransferManager;

    public String generateUniqueAccountNumber() {
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            String candidate = AccountNumberGenerator.generateCandidateAccountNumber();
            if (!accountNumberExists(candidate)) {
                return candidate;
            }
        }
        throw new BusinessException("Failed to generate a unique account number after " + MAX_ATTEMPTS + " attempts.");
    }

    private boolean accountNumberExists(String accountNumber) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(a) FROM Account a WHERE a.accountNumber = :acc", Long.class
        );
        query.setParameter("acc", accountNumber);
        return query.getSingleResult() > 0;
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "ADMIN", "USER"})
    public boolean createAccount(AccountRequest accountRequest) {
        if (accountRequest.getCustomerId() <= 0) {
            throw new BusinessException("Customer ID must be greater than 0");
        }
        if (accountRequest.getAccountType() == null || accountRequest.getAccountType().isBlank()) {
            throw new BusinessException("Account type cannot be null or empty");
        }

        AccountType type;
        try {
            type = AccountType.valueOf(accountRequest.getAccountType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid account type: " + accountRequest.getAccountType());
        }

        Customer customer = em.find(Customer.class, accountRequest.getCustomerId());
        if (customer == null) {
            throw new BusinessException("Customer with ID " + accountRequest.getCustomerId() + " does not exist");
        }

        String accountNumber = generateUniqueAccountNumber();

        Account account = new Account(customer, accountRequest.getAccountName(), accountNumber, type);
        em.persist(account);
        return true;
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public boolean createUserAndAccount(UserRegisterDTO dto) {
        if (dto == null) throw new BusinessException("Request cannot be null");
        if (dto.getUsername() == null || dto.getUsername().isBlank())
            throw new BusinessException("Username is required");
        if (dto.getPassword() == null || dto.getPassword().isBlank())
            throw new BusinessException("Password is required");
        if (dto.getFullName() == null || dto.getFullName().isBlank())
            throw new BusinessException("Full name is required");
        if (dto.getEmail() == null || dto.getEmail().isBlank())
            throw new BusinessException("Email is required");
        if (dto.getAccountName() == null || dto.getAccountName().isBlank())
            throw new BusinessException("Account name is required");
        if (dto.getType() == null || dto.getType().isBlank())
            throw new BusinessException("Account type is required");
        if (dto.getBalance() == null || dto.getBalance() < 0)
            throw new BusinessException("Initial balance must be non-negative");
        if (dto.getRole() == null || dto.getRole().isBlank())
            throw new BusinessException("Role is required");

        try {
            // Check for existing user
            User existingUser = em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", dto.getUsername())
                    .getResultStream().findFirst().orElse(null);
            if (existingUser != null) throw new BusinessException("Username already exists");

            Customer existingCustomer = em.createNamedQuery("Customer.findByEmail", Customer.class)
                    .setParameter("email", dto.getEmail())
                    .getResultStream().findFirst().orElse(null);
            if (existingCustomer != null) throw new BusinessException("Email already exists");

            // Create user
            RoleType role = RoleType.valueOf(dto.getRole().toUpperCase());
            String hashedPassword = PasswordUtil.hash(dto.getPassword());
            User user = new User(dto.getUsername(), hashedPassword, role, java.time.LocalDateTime.now());
            em.persist(user);

            // Create customer
            Customer customer = new Customer(user, dto.getFullName(), dto.getEmail(), dto.getPhone(), dto.getAddress());
            em.persist(customer);

            // Create account
            AccountType accountType = AccountType.valueOf(dto.getType().toUpperCase());
            String accountNumber = generateUniqueAccountNumber();
            Account account = new Account(customer, dto.getAccountName(), accountNumber, accountType);
            em.persist(account);

            // Initial deposit (if balance > 0)
            if (dto.getBalance() > 0) {
                account.setBalance(dto.getBalance());
                em.merge(account);
                Transaction transaction = new Transaction(
                        null, // fromAccount is null for deposit
                        account,
                        dto.getBalance(),
                        TransactionType.DEPOSIT,
                        TransactionStatus.SUCCESS,
                        java.time.LocalDateTime.now(),
                        "Initial deposit"
                );
                em.persist(transaction);
            }else{
                throw new InsufficientFundException("Initial balance must be greater than 0 for account creation");
            }

            return true;
        } catch (Exception e) {
            throw new BusinessException("Failed to create user and account: " + e.getMessage());
        }
    }

    @Override
    public AccountDTO getAccountById(int id) {
        if (id <= 0) {
            throw new BusinessException("Account ID must be greater than 0");
        }

        Account account = em.find(Account.class, id);
        if (account == null) {
            throw new BusinessException("Account with ID " + id + " does not exist");
        }

        return new AccountDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountName(),
                account.getBalance(),
                account.getAccountType().name(),
                account.getStatus().name(),
                account.getCustomer().getId(),
                account.getCreatedAt(),
                account.getCustomer().getFullName()
        );

    }

    @Override
    public AccountDTO getAccountByAccountNumber(String accountNumber) {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new BusinessException("Account number cannot be null or empty");
        }

        TypedQuery<Account> query = em.createNamedQuery("Account.findByAccountNumber", Account.class);
        query.setParameter("accountNumber", accountNumber);

        try {
            Account account = query.getSingleResult();
            return new AccountDTO(
                    account.getId(),
                    account.getAccountNumber(),
                    account.getAccountName(),
                    account.getBalance(),
                    account.getAccountType().name(),
                    account.getStatus().name(),
                    account.getCustomer().getId(),
                    account.getCreatedAt(),
                    account.getCustomer().getFullName()
            );
        } catch (NoResultException e) {
            throw new BusinessException("Account with number " + accountNumber + " does not exist");
        }

    }

    @Override
    public List<AccountDTO> getAccountsByCustomerId(int customerId) {

        if (customerId <= 0) {
            throw new BusinessException("Customer ID must be greater than 0");
        }

        TypedQuery<Account> query = em.createNamedQuery("Account.findByCustomerId", Account.class);
        query.setParameter("customerId", customerId);

        List<Account> accounts = query.getResultList();
        if (accounts.isEmpty()) {
            return List.of(); // Return an empty list if no accounts found
        }

        return accounts.stream().map(account -> new AccountDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountName(),
                account.getBalance(),
                account.getAccountType().name(),
                account.getStatus().name(),
                account.getCustomer().getId(),
                account.getCreatedAt(),
                account.getCustomer().getFullName()
        )).toList();

    }

    @Override
    public List<AccountDTO> getActiveAccountsByCustomerId(int customerId) {
        if (customerId <= 0) {
            throw new BusinessException("Customer ID must be greater than 0");
        }

        TypedQuery<Account> query = em.createNamedQuery("Account.findByCustomerIdAndStatus", Account.class);
        query.setParameter("customerId", customerId);
        query.setParameter("status", AccountStatus.ACTIVE);

        List<Account> accounts = query.getResultList();
        if (accounts.isEmpty()) {
            return List.of(); // Return an empty list if no accounts found
        }

        return accounts.stream().map(account -> new AccountDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountName(),
                account.getBalance(),
                account.getAccountType().name(),
                account.getStatus().name(),
                account.getCustomer().getId(),
                account.getCreatedAt(),
                account.getCustomer().getFullName()
        )).toList();
    }

    @Override
    public boolean closeAccount(String accountNumber) {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new BusinessException("Account number cannot be null or empty");
        }

        try {
            Account account = em.createNamedQuery("Account.findByAccountNumber", Account.class)
                    .setParameter("accountNumber", accountNumber)
                    .getSingleResult();

            if (account == null) {
                throw new BusinessException("Account with number " + accountNumber + " does not exist");
            }

            account.setStatus(AccountStatus.INACTIVE);
            em.merge(account);
            return true;
        } catch (NoResultException e) {
            throw new BusinessException("Account with number " + accountNumber + " does not exist");
        } catch (Exception e) {
            throw new BusinessException("An error occurred while closing the account: " + e.getMessage());
        }

    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public boolean deleteAccount(int accountId) {
        return false;
    }

    @Override
    public int getTotalTransactions() {

        try {
            long count = em.createQuery("SELECT COUNT(t) FROM Transaction t", Long.class).getSingleResult();
            return (int) count;
        } catch (NoResultException e) {
            return 0; // No transactions found, return 0
        } catch (Exception e) {
            throw new BusinessException("An error occurred while fetching total transactions: " + e.getMessage());
        }

    }

    @Override
    public int getTotalAccounts() {

        try {
            long count = em.createQuery("SELECT COUNT(a) FROM Account a", Long.class).getSingleResult();
            return (int) count;
        } catch (NoResultException e) {
            return 0; // No accounts found, return 0
        } catch (Exception e) {
            throw new BusinessException("An error occurred while fetching total accounts: " + e.getMessage());
        }

    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public List<AccountTableResDTO> filterAccounts(String searchText, String accountType, String accountStatus) {
        try {
            StringBuilder jpql = new StringBuilder(
                    "SELECT a FROM Account a JOIN a.customer c JOIN c.user u WHERE 1=1"
            );
            if (searchText != null && !searchText.isBlank()) {
                jpql.append(" AND (LOWER(u.username) LIKE :searchText OR LOWER(c.fullName) LIKE :searchText)");
            }
            if (accountType != null && !accountType.isBlank() && !"all".equalsIgnoreCase(accountType)) {
                jpql.append(" AND a.accountType = :accountType");
            }
            if (accountStatus != null && !accountStatus.isBlank() && !"all".equalsIgnoreCase(accountStatus)) {
                jpql.append(" AND a.status = :accountStatus");
            }
            jpql.append(" ORDER BY a.id DESC");

            TypedQuery<Account> query = em.createQuery(jpql.toString(), Account.class);

            if (searchText != null && !searchText.isBlank()) {
                query.setParameter("searchText", "%" + searchText.toLowerCase() + "%");
            }
            if (accountType != null && !accountType.isBlank() && !"all".equalsIgnoreCase(accountType)) {
                query.setParameter("accountType", AccountType.valueOf(accountType.toUpperCase()));
            }
            if (accountStatus != null && !accountStatus.isBlank() && !"all".equalsIgnoreCase(accountStatus)) {
                query.setParameter("accountStatus", AccountStatus.valueOf(accountStatus.toUpperCase()));
            }

            List<Account> accounts = query.getResultList();
            if (accounts.isEmpty()) {
                return List.of();
            }
            return accounts.stream().map(a -> new AccountTableResDTO(
                    a.getId(),
                    a.getCustomer().getFullName(),
                    a.getCustomer().getEmail(),
                    a.getCustomer().getUser().getUsername(),
                    a.getAccountName(),
                    a.getAccountType().name(),
                    a.getBalance(),
                    a.getStatus().name()
            )).toList();
        } catch (NoResultException e) {
            return List.of();
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid account type or status");
        } catch (Exception e) {
            throw new BusinessException("Error filtering accounts: " + e.getMessage());
        }
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN"})
    public List<InterestResDTO> getDalyInterestLog() {

        try {
            List<InterestResDTO> logList = em.createNamedQuery("InterestLog.findDalyLog", InterestLog.class)
                    .setParameter("appliedDate", LocalDate.now())
                    .getResultList()
                    .stream()
                    .map(interestLog -> new InterestResDTO(
                            interestLog.getAccount().getAccountNumber(),
                            interestLog.getRateApplied().toString(),
                            interestLog.getAppliedDate().toString()
                    )).toList();
            return logList.isEmpty() ? List.of() : logList; // Return an empty list if no logs found
        } catch (NoResultException e) {
            return List.of(); // No logs found, return an empty list
        } catch (Exception e) {
            throw new BusinessException("An error occurred while fetching daily interest logs: " + e.getMessage());
        }

    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public List<InterestResDTO> getAllInterestLog() {

        try {
            List<InterestResDTO> logList = em.createQuery("SELECT il FROM InterestLog il", InterestLog.class)
                    .getResultList()
                    .stream()
                    .map(interestLog -> new InterestResDTO(
                            interestLog.getAccount().getAccountNumber(),
                            interestLog.getRateApplied().toString(),
                            interestLog.getAppliedDate().toString()
                    )).toList();
            return logList.isEmpty() ? List.of() : logList; // Return an empty list if no logs found
        } catch (NoResultException e) {
            return List.of(); // No logs found, return an empty list
        } catch (Exception e) {
            throw new BusinessException("An error occurred while fetching all interest logs: " + e.getMessage());
        }

    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public List<TransactionHistoryResDTO> getDailyTransactionHistory() {

        try {
            List<Transaction> transactions = em.createQuery(
                            "SELECT t FROM Transaction t WHERE FUNCTION('DATE', t.transactionTime) = :date",
                            Transaction.class)
                    .setParameter("date", LocalDate.now())
                    .getResultList();

            if (transactions.isEmpty()) {
                return List.of(); // Return an empty list if no transactions found
            }

            return transactions.stream().map(transaction -> new TransactionHistoryResDTO(
                    transaction.getId(),
                    transaction.getFromAccount() != null ? transaction.getFromAccount().getAccountNumber() : "ATHENA BANK",
                    transaction.getToAccount() != null ? transaction.getToAccount().getAccountNumber() : "ATHENA BANK",
                    transaction.getAmount(),
                    transaction.getType().name(),
                    transaction.getStatus().name(),
                    transaction.getTransactionTime().toString(),
                    transaction.getDescription()
            )).toList();

        } catch (NoResultException e) {
            return List.of(); // No transactions found, return an empty list
        } catch (Exception e) {
            throw new BusinessException("An error occurred while fetching daily transaction history: " + e.getMessage());
        }

    }

    @Override
    public List<UserTransactionResDTO> getAllTransactionList(int customerId) {

        if (customerId <= 0) {
            throw new BusinessException("Customer ID must be greater than 0");
        }


        TypedQuery<Transaction> query = em.createNamedQuery("Transaction.findByCustomerId", Transaction.class);
        query.setParameter("customerId", customerId);

        List<Transaction> transactions = query.getResultList();
        if (transactions.isEmpty()) {
            return List.of(); // Return an empty list if no transactions found
        }
        return transactions.stream().map(transaction -> new UserTransactionResDTO(
                transaction.getDescription(),
                transaction.getAmount().toString(),
                transaction.getTransactionTime().toString()
        )).toList();

    }

    @Override
    public boolean deposit(int accountId, double amount) {
        return false;
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "ADMIN", "USER"})
    public boolean transfer(FundTransferRequest fundTransferRequest) {
        if ((fundTransferRequest.getFromAccountId() <= 0 && (fundTransferRequest.getFromAccountNumber() == null || fundTransferRequest.getFromAccountNumber().isBlank()))) {
            throw new BusinessException("Source account ID or number must be provided");
        }
        if (fundTransferRequest.getToAccountNumber() == null || fundTransferRequest.getToAccountNumber().isBlank()) {
            throw new BusinessException("Recipient account number cannot be null or empty");
        }
        if (fundTransferRequest.getAmount() <= 0) {
            throw new InsufficientFundException("Transfer amount must be greater than 0");
        }

        try {
            Account from;
            if (fundTransferRequest.getFromAccountId() > 0) {
                from = em.find(Account.class, fundTransferRequest.getFromAccountId());
                if (from == null) {
                    throw new BusinessException("Invalid source account ID");
                }
            } else {
                from = em.createNamedQuery("Account.findByAccountNumber", Account.class)
                        .setParameter("accountNumber", fundTransferRequest.getFromAccountNumber())
                        .getSingleResult();
            }

            Account to = em.createNamedQuery("Account.findByAccountNumber", Account.class)
                    .setParameter("accountNumber", fundTransferRequest.getToAccountNumber())
                    .getSingleResult();

            if (from.getAccountNumber().equals(to.getAccountNumber())) {
                throw new BusinessException("Cannot transfer to the same account");
            }

            if (from.getBalance() < fundTransferRequest.getAmount()) {
                throw new InsufficientFundException("Insufficient funds in source account");
            }

            from.setBalance(from.getBalance() - fundTransferRequest.getAmount());
            to.setBalance(to.getBalance() + fundTransferRequest.getAmount());

            em.merge(from);
            em.merge(to);

            Transaction transaction = new Transaction(
                    from,
                    to,
                    fundTransferRequest.getAmount(),
                    TransactionType.TRANSFER,
                    TransactionStatus.SUCCESS,
                    LocalDateTime.now(),
                    fundTransferRequest.getDescription()
            );

            em.persist(transaction);
            return true;
        } catch (NoResultException e) {
            throw new BusinessException("Account not found");
        } catch (Exception e) {
            throw new BusinessException("An error occurred while processing the transfer: " + e.getMessage());
        }
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "ADMIN", "USER"})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean scheduleTransfer(ScheduleTransferRequestDTO scheduleTransferRequestDTO) {
        if ((scheduleTransferRequestDTO.getFromAccountId() <= 0 && (scheduleTransferRequestDTO.getFromAccountNumber() == null || scheduleTransferRequestDTO.getFromAccountNumber().isBlank()))) {
            throw new BusinessException("Source account ID or number must be provided");
        }
        if (scheduleTransferRequestDTO.getToAccountNumber() == null || scheduleTransferRequestDTO.getToAccountNumber().isBlank()) {
            throw new BusinessException("Recipient account number cannot be null or empty");
        }
        if (scheduleTransferRequestDTO.getAmount() <= 0) {
            throw new InsufficientFundException("Transfer amount must be greater than 0");
        }
        if (scheduleTransferRequestDTO.getTransferDate() == null) {
            throw new BusinessException("Transaction time cannot be null");
        }
        if (scheduleTransferRequestDTO.getDescription() == null || scheduleTransferRequestDTO.getDescription().isBlank()) {
            throw new BusinessException("Description cannot be null or empty");
        }

        try {
            Account from;
            if (scheduleTransferRequestDTO.getFromAccountId() > 0) {
                from = em.find(Account.class, scheduleTransferRequestDTO.getFromAccountId());
                if (from == null) {
                    throw new BusinessException("Invalid source account ID");
                }
            } else {
                from = em.createNamedQuery("Account.findByAccountNumber", Account.class)
                        .setParameter("accountNumber", scheduleTransferRequestDTO.getFromAccountNumber())
                        .getSingleResult();
            }

            Account to = em.createNamedQuery("Account.findByAccountNumber", Account.class)
                    .setParameter("accountNumber", scheduleTransferRequestDTO.getToAccountNumber())
                    .getSingleResult();

            LocalDateTime scheduledTime = LocalDateTime.parse(scheduleTransferRequestDTO.getTransferDate().replace(" "
                    , "T"));

            Transaction transaction = new Transaction(
                    from,
                    to,
                    scheduleTransferRequestDTO.getAmount(),
                    TransactionType.TRANSFER,
                    TransactionStatus.PENDING,
                    scheduledTime,
                    scheduleTransferRequestDTO.getDescription()
            );

            em.persist(transaction); // Save to DB
            em.flush();

            // Schedule the timer
            scheduledTransferManager.scheduleTransferTimer(transaction);
            return true;
        } catch (NoResultException e) {
            throw new BusinessException("Recipient does not exist");
        } catch (Exception e) {
            throw new BusinessException("An error occurred while scheduling the transfer: " + e.getMessage());
        }
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "ADMIN", "USER"})
    public List<TransactionHistoryResDTO> getTransactionHistoryByCustomer(int customerId, String date) {
        if (customerId <= 0) {
            throw new BusinessException("Customer ID must be greater than 0");
        }

        if (date == null) {
            try {
                TypedQuery<Transaction> query = em.createNamedQuery("Transaction.findByCustomerId", Transaction.class);
                query.setParameter("customerId", customerId);

                List<Transaction> transactions = query.getResultList();
                if (transactions.isEmpty()) {
                    return List.of(); // Return an empty list if no transactions found
                }

                return transactions.stream().map(transaction -> new TransactionHistoryResDTO(
                        transaction.getId(),
                        transaction.getFromAccount() != null ? transaction.getFromAccount().getAccountNumber() : "ATHENA BANK",
                        transaction.getToAccount() != null ? transaction.getToAccount().getAccountNumber() : "ATHENA BANK",
                        transaction.getAmount(),
                        transaction.getType().name(),
                        transaction.getStatus().name(),
                        transaction.getTransactionTime().toString(),
                        transaction.getDescription()
                )).toList();

            } catch (NoResultException e) {
                throw new BusinessException("No transactions found for customer with ID " + customerId);
            }
        } else {

            try {

                List<Transaction> resultList = em.createQuery("SELECT t FROM Transaction t WHERE t.fromAccount" +
                                        ".customer.id = :customerId AND FUNCTION('DATE', t.transactionTime) = :date",
                                Transaction.class)
                        .setParameter("customerId", customerId)
                        .setParameter("date", LocalDate.parse(date))
                        .getResultList();

                if (resultList.isEmpty()) {
                    return List.of(); // Return an empty list if no transactions found
                }

                return resultList.stream().map(transaction -> new TransactionHistoryResDTO(
                        transaction.getId(),
                        transaction.getFromAccount().getAccountNumber(),
                        transaction.getToAccount() != null ? transaction.getToAccount().getAccountNumber() : "N/A",
                        transaction.getAmount(),
                        transaction.getType().name(),
                        transaction.getStatus().name(),
                        transaction.getTransactionTime().toString(),
                        transaction.getDescription()
                )).toList();

            } catch (NoResultException e) {
                throw new BusinessException("No transactions found for customer with ID " + customerId + " on date " + date);
            } catch (Exception e) {
                throw new BusinessException("An error occurred while fetching transaction history: " + e.getMessage());
            }

        }
    }

    @Override
    public List<TransactionHistoryResDTO> getTransactionHistory(TransactionHistoryReqDTO thrd) {
        String accountNumber = thrd.getAccountNumber();
        String date = thrd.getDate();

        try {

            String baseQuery = "SELECT t FROM Transaction t";
            String whereClause = "";
            boolean hasAccount = accountNumber != null && !accountNumber.isBlank();
            boolean hasDate = date != null && !date.isBlank();

            if (hasAccount && hasDate) {
                whereClause = " WHERE (t.fromAccount.accountNumber = :accountNumber OR t.toAccount.accountNumber = " +
                        ":accountNumber) AND FUNCTION('DATE', t.transactionTime) = :date";
            } else if (hasAccount) {
                whereClause = " WHERE t.fromAccount.accountNumber = :accountNumber OR t.toAccount.accountNumber = " +
                        ":accountNumber";
            } else if (hasDate) {
                whereClause = " WHERE FUNCTION('DATE', t.transactionTime) = :date";
            }
            String finalQuery = baseQuery + whereClause + " ORDER BY t.transactionTime DESC";

            var query = em.createQuery(finalQuery, Transaction.class);
            if (hasAccount) {
                query.setParameter("accountNumber", accountNumber);
            }
            if (hasDate) {
                query.setParameter("date", LocalDate.parse(date));
            }

            List<Transaction> transactions = query.getResultList();
            if (transactions.isEmpty()) {
                return List.of();
            }
            return transactions.stream().map(transaction -> new TransactionHistoryResDTO(
                    transaction.getId(),
                    transaction.getFromAccount() != null ? transaction.getFromAccount().getAccountNumber() : "ATHENA BANK",
                    transaction.getToAccount() != null ? transaction.getToAccount().getAccountNumber() : "ATHENA BANK",
                    transaction.getAmount(),
                    transaction.getType().name(),
                    transaction.getStatus().name(),
                    transaction.getTransactionTime().toString(),
                    transaction.getDescription()
            )).toList();

        } catch (NoResultException e) {
            throw new BusinessException("No transactions found for the provided criteria");
        } catch (Exception e) {
            throw new BusinessException("An error occurred while fetching transaction history: " + e.getMessage());
        }
    }

    @Override
    public double getBalance(int accountId) {

        if (accountId <= 0) {
            throw new BusinessException("Account ID must be greater than 0");
        }

        Account account = em.find(Account.class, accountId);
        if (account == null) {
            throw new BusinessException("Account with ID " + accountId + " does not exist");
        }

        return account.getBalance();

    }

    @Override
    public double getAllBalance(int customerId) {

        if (customerId <= 0) {
            throw new BusinessException("Customer ID must be greater than 0");
        }

        try {

            TypedQuery<Double> query = em.createQuery(
                    "SELECT SUM(a.balance) FROM Account a WHERE a.customer.id = :customerId", Double.class
            );

            query.setParameter("customerId", customerId);
            Double totalBalance = query.getSingleResult();

            return totalBalance != null ? totalBalance : 0.0; // Return 0 if no accounts found

        } catch (NoResultException e) {
            throw new BusinessException("No accounts found for customer with ID " + customerId);
        }

    }

    @Override
    public boolean isAccountActive(int accountId) {

        if (accountId <= 0) {
            throw new BusinessException("Account ID must be greater than 0");
        }

        Account account = em.find(Account.class, accountId);
        if (account == null) {
            throw new BusinessException("Account with ID " + accountId + " does not exist");
        }

        return "ACTIVE".equalsIgnoreCase(account.getStatus().name());

    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return em.createNamedQuery("Account.findAll", AccountDTO.class).getResultList();
    }
}