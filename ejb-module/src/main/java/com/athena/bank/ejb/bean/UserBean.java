package com.athena.bank.ejb.bean;

import com.athena.bank.core.dto.AdminRequestDTO;
import com.athena.bank.core.dto.RegisterDTO;
import com.athena.bank.core.dto.UserResDTO;
import com.athena.bank.core.exception.BusinessException;
import com.athena.bank.core.model.Customer;
import com.athena.bank.core.model.RoleType;
import com.athena.bank.core.model.User;
import com.athena.bank.core.model.UserStatus;
import com.athena.bank.core.service.UserService;
import com.athena.bank.ejb.util.EmailValidator;
import com.athena.bank.ejb.util.PasswordUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class UserBean implements UserService {

    @PersistenceContext
    private EntityManager em;


    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean registerUser(RegisterDTO registerDTO) {

        // ───── Basic Field Validations ─────
        if (registerDTO.getFullName() == null || registerDTO.getFullName().isBlank())
            throw new BusinessException("Full name cannot be null or empty");
        if (registerDTO.getEmail() == null || registerDTO.getEmail().isBlank())
            throw new BusinessException("Email cannot be null or empty");
        if (!EmailValidator.isValid(registerDTO.getEmail())) {
            throw new BusinessException("Invalid email format");
        }
        if (registerDTO.getUsername() == null || registerDTO.getUsername().isBlank())
            throw new BusinessException("Username cannot be null or empty");
        if (registerDTO.getPassword() == null || registerDTO.getPassword().isBlank())
            throw new BusinessException("Password cannot be null or empty");
        if (registerDTO.getPhone() == null || registerDTO.getPhone().isBlank())
            throw new BusinessException("Phone cannot be null or empty");
        if (registerDTO.getAddress() == null || registerDTO.getAddress().isBlank())
            throw new BusinessException("Address cannot be null or empty");
        if (registerDTO.getRole() == null || registerDTO.getRole().isBlank())
            throw new BusinessException("Role cannot be null or empty");

        try {
            // ───── Duplicate Username Check ─────
            User existingUser = em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", registerDTO.getUsername())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (existingUser != null) {
                throw new BusinessException("Username already exists");
            }

            // ───── Duplicate Email Check ─────
            Customer existingCustomer = em.createNamedQuery("Customer.findByEmail", Customer.class)
                    .setParameter("email", registerDTO.getEmail())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (existingCustomer != null) {
                throw new BusinessException("Email already exists");
            }

            // ───── Parse Role Type ─────
            RoleType role;
            try {
                role = RoleType.valueOf(registerDTO.getRole().toUpperCase());
            } catch (BusinessException e) {
                throw new BusinessException("Invalid role type: " + registerDTO.getRole());
            }

            // ───── Hash Password ─────
            String hashedPassword = PasswordUtil.hash(registerDTO.getPassword());

            // ───── Create and Persist User ─────
            User user = new User(registerDTO.getUsername(), hashedPassword, role, LocalDateTime.now());
            em.persist(user);

            // ───── Create and Persist Customer ─────
            Customer customer = new Customer(user,
                    registerDTO.getFullName(),
                    registerDTO.getEmail(),
                    registerDTO.getPhone(),
                    registerDTO.getAddress());
            em.persist(customer);

            return true;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN"})
    public boolean registerAdmin(AdminRequestDTO ard) {

        if (ard.getFullName() == null || ard.getFullName().isBlank()) {
            throw new BusinessException("Full name cannot be null or empty");
        }
        if (ard.getEmail() == null || ard.getEmail().isBlank()) {
            throw new BusinessException("Email cannot be null or empty");
        }
        if (!EmailValidator.isValid(ard.getEmail())) {
            throw new BusinessException("Invalid email format");
        }
        if (ard.getUsername() == null || ard.getUsername().isBlank()) {
            throw new BusinessException("Username cannot be null or empty");
        }
        if (ard.getPassword() == null || ard.getPassword().isBlank()) {
            throw new BusinessException("Password cannot be null or empty");
        }
        if (ard.getPhone() == null || ard.getPhone().isBlank()) {
            throw new BusinessException("Phone cannot be null or empty");
        }
        if (ard.getAddress() == null || ard.getAddress().isBlank()) {
            throw new BusinessException("Address cannot be null or empty");
        }

        try {
            // ───── Duplicate Username Check ─────
            User existingUser = em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", ard.getUsername())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (existingUser != null) {
                throw new BusinessException("Username already exists");
            }

            // ───── Duplicate Email Check ─────
            Customer existingCustomer = em.createNamedQuery("Customer.findByEmail", Customer.class)
                    .setParameter("email", ard.getEmail())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (existingCustomer != null) {
                throw new BusinessException("Email already exists");
            }

            // ───── Hash Password ─────
            String hashedPassword = PasswordUtil.hash(ard.getPassword());

            // ───── Create and Persist User ─────
            User user = new User(ard.getUsername(), hashedPassword, RoleType.ADMIN, LocalDateTime.now());
            em.persist(user);

            // ───── Create and Persist Customer ─────
            Customer customer = new Customer(user,
                    ard.getFullName(),
                    ard.getEmail(),
                    ard.getPhone(),
                    ard.getAddress());
            em.persist(customer);

            return true;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password", e);
        }

    }

    @Override
    public boolean validate(String username, String password) {

        // ───── Basic Field Validations ─────
        if (username == null || username.isBlank()) {
            throw new BusinessException("Username cannot be null or empty");
        } else if (password == null || password.isBlank()) {
            throw new BusinessException("Password cannot be null or empty");
        }

        try {
            User user = em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            // ───── Verify Password ─────

            return PasswordUtil.verify(password, user.getPassword());

        } catch (NoResultException e) {
//            throw new BusinessException("Invalid username or password");
            // If no user is found, we can return false or throw an exception
            return false;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public boolean updateUserStatus(int userId, String status) {

        if (userId <= 0) {
            throw new BusinessException("User ID must be a positive integer");
        }
        if (status == null || status.isBlank()) {
            throw new BusinessException("Status cannot be null or empty");
        }

        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                throw new BusinessException("User not found with ID: " + userId);
            }

            // ───── Parse Status ─────
            UserStatus userStatus;
            try {
                userStatus = UserStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BusinessException("Invalid status type: " + status);
            }

            // ───── Update Status ─────
            user.setStatus(userStatus);
            em.merge(user);
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Error updating user status", e);
        }

    }

    @Override
    public boolean updateUserDetails(int userId, String newUsername, String newPassword) {
        return false;
    }

    @Override
    public boolean deleteUser(int userId) {
        return false;
    }

    @Override
    public String getUserRole(String username) {
        // ───── Basic Field Validations ─────
        if (username == null || username.isBlank()) {
            throw new BusinessException("Username cannot be null or empty");
        }

        try {
            User user = em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return user.getRole().name();
        } catch (NoResultException e) {
            throw new BusinessException("User not found with username: " + username);
        }
    }

    @Override
    public int getActiveUserCount() {
        try {
            Long count = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.status = :status", Long.class)
                    .setParameter("status", UserStatus.ACTIVE) // Example threshold of 30 minutes
                    .getSingleResult();
            return count.intValue();
        } catch (NoResultException e) {
            // If no users are found, return 0
            return 0;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving active user count", e);
        }
    }

    @Override
    public int getActiveCustomersCount() {

        try {
            Long count = em.createQuery("SELECT COUNT(c) FROM Customer c WHERE c.user.status = :status AND c.user" +
                            ".role = :role", Long.class)
                    .setParameter("status", UserStatus.ACTIVE)
                    .setParameter("role", RoleType.USER)
                    .getSingleResult();
            return count.intValue();
        } catch (NoResultException e) {
            // If no customers are found, return 0
            return 0;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving active customer count", e);
        }

    }

    @Override
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public List<UserResDTO> filterUsers(String searchText, String status, String type) {
        try {
            StringBuilder jpql = new StringBuilder("SELECT u FROM User u WHERE u.role <> :superAdminRole");
            if (searchText != null && !searchText.isBlank()) {
                jpql.append(" AND LOWER(u.username) LIKE :searchText");
            }
            if (status != null && !"all".equalsIgnoreCase(status)) {
                jpql.append(" AND u.status = :status");
            }
            if (type != null && !"all".equalsIgnoreCase(type)) {
                jpql.append(" AND u.role = :role");
            }

            TypedQuery<User> query = em.createQuery(jpql.toString(), User.class);
            query.setParameter("superAdminRole", RoleType.SUPER_ADMIN);

            if (searchText != null && !searchText.isBlank()) {
                query.setParameter("searchText", "%" + searchText.toLowerCase() + "%");
            }
            if (status != null && !"all".equalsIgnoreCase(status)) {
                query.setParameter("status", UserStatus.valueOf(status.toUpperCase()));
            }
            if (type != null && !"all".equalsIgnoreCase(type)) {
                query.setParameter("role", RoleType.valueOf(type.toUpperCase()));
            }

            List<User> users = query.getResultList();

            // Map User to UserResDTO, fetch Customer by user id
            return users.stream()
                    .map(u -> {
                        Customer customer = em.find(Customer.class, u.getId());
                        String email = customer != null ? customer.getEmail() : "";
                        return new UserResDTO(
                                u.getId(),
                                u.getUsername(),
                                email,
                                u.getRole().name(),
                                u.getStatus().name(),
                                customer.getFullName()
                        );
                    })
                    .toList();

        } catch (NoResultException e) {
            return List.of();
        } catch (Exception e) {
            throw new RuntimeException("Error filtering users", e);
        }
    }
}
