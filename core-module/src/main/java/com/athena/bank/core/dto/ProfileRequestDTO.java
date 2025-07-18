package com.athena.bank.core.dto;

import java.io.Serializable;

public class ProfileRequestDTO implements Serializable {

    private int id;
    private String fullName;
    private String username;
    private String phone;
    private String address;

    public ProfileRequestDTO(int id, String fullName, String username, String phone, String address) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
