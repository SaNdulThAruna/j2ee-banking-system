package com.athena.bank.core.dto;

import java.io.Serializable;

public class UserProfileResDTO implements Serializable {

    private String fullName;
    private String email;
    private String username;
    private String phone;
    private String address;

    public UserProfileResDTO(String fullName, String email, String username, String phone, String address) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
