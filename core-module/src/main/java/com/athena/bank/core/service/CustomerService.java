package com.athena.bank.core.service;

import com.athena.bank.core.dto.ProfileRequestDTO;
import com.athena.bank.core.dto.UserProfileResDTO;
import jakarta.ejb.Remote;

@Remote
public interface CustomerService {

    int getCustomerIdByUsername(String username); // Get customer ID by username
    UserProfileResDTO getCustomerProfile(int customerId); // Get customer profile by customer ID
    boolean updateUserProfile(ProfileRequestDTO profileRequestDTO); // Update user profile information
}
