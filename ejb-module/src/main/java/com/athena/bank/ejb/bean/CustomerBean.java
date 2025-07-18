package com.athena.bank.ejb.bean;

import com.athena.bank.core.dto.ProfileRequestDTO;
import com.athena.bank.core.dto.UserProfileResDTO;
import com.athena.bank.core.exception.BusinessException;
import com.athena.bank.core.model.Customer;
import com.athena.bank.core.model.User;
import com.athena.bank.core.service.CustomerService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Stateless
public class CustomerBean implements CustomerService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int getCustomerIdByUsername(String username) {
        try {
            Customer customer = em.createNamedQuery("Customer.findByUsername", Customer.class)
                    .setParameter("username", username)
                    .getSingleResult();

            return customer.getId();

        } catch (NoResultException e) {
            return 0;
        }
    }

    @Override
    public UserProfileResDTO getCustomerProfile(int customerId) {

        if (customerId <= 0) {
            throw new BusinessException("Invalid customer ID: " + customerId);
        }

        try {
            Customer customer = em.find(Customer.class, customerId);
            if (customer == null) {
                throw new BusinessException("Customer not found with ID: " + customerId);
            }

            return new UserProfileResDTO(
                    customer.getFullName(),
                    customer.getEmail(),
                    customer.getUser().getUsername(),
                    customer.getPhone(),
                    customer.getAddress()
            );
        } catch (NoResultException e) {
            throw new BusinessException("Customer not found with ID: " + customerId, e);
        }
    }

    @Override
    public boolean updateUserProfile(ProfileRequestDTO profileRequestDTO) {
        if (profileRequestDTO.getId() <= 0) {
            throw new BusinessException("Invalid customer ID: " + profileRequestDTO.getId());
        }
        if (profileRequestDTO.getUsername() == null) {
            throw new BusinessException("Username cannot be null");
        }
        if (profileRequestDTO.getFullName() == null) {
            throw new BusinessException("Full name cannot be null");
        }
        if (profileRequestDTO.getPhone() == null) {
            throw new BusinessException("Phone cannot be null");
        }
        if (profileRequestDTO.getAddress() == null) {
            throw new BusinessException("Address cannot be null");
        }
        try {
            Customer customer = em.find(Customer.class, profileRequestDTO.getId());
            if (customer == null) {
                throw new BusinessException("Customer not found with ID: " + profileRequestDTO.getId());
            }

            User user = customer.getUser();
            if (!user.getUsername().equals(profileRequestDTO.getUsername())) {
                // Check if the new username is already taken by another user
                try {
                    User existingUser = em.createNamedQuery("User.findByUsername", User.class)
                            .setParameter("username", profileRequestDTO.getUsername())
                            .getSingleResult();
                    // If found and not the same user, throw exception
                    if (existingUser != null && existingUser.getId() != user.getId()) {
                        throw new BusinessException("Username already exists: " + profileRequestDTO.getUsername());
                    }
                } catch (NoResultException ignored) {
                    // Username is available
                }
                user.setUsername(profileRequestDTO.getUsername());
            }

            customer.setFullName(profileRequestDTO.getFullName());
            customer.setAddress(profileRequestDTO.getAddress());
            customer.setPhone(profileRequestDTO.getPhone());

            em.merge(user);
            em.merge(customer);

            return true;
        } catch (NoResultException e) {
            throw new BusinessException("Customer not found with ID: " + profileRequestDTO.getId(), e);
        }
    }
}
