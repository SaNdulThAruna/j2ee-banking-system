package com.athena.bank.core.service;

import com.athena.bank.core.dto.AdminRequestDTO;
import com.athena.bank.core.dto.RegisterDTO;
import com.athena.bank.core.dto.UserResDTO;
import jakarta.ejb.Remote;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Remote
public interface UserService{

    boolean registerUser(RegisterDTO registerDTO) throws NoSuchAlgorithmException, InvalidKeySpecException; // Register a new user

    boolean registerAdmin(AdminRequestDTO ard); // Register a new admin

    boolean validate(String email, String password); // Validate user credentials

    boolean updateUserStatus(int userId, String status); // Update user status (e.g., active, inactive)

    boolean updateUserDetails(int userId, String newUsername, String newPassword); // Update user details

    boolean deleteUser(int userId); // Delete a user

    String getUserRole(String username); // Get the role of a user

    int getActiveUserCount(); // Get the count of active users

    int getActiveCustomersCount(); // Get the count of active customers

    List<UserResDTO> filterUsers(String searchText, String status, String type); // Filter users based on search text, status, and type

}
