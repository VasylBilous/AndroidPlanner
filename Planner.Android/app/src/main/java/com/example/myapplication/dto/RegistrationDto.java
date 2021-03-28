package com.example.myapplication.dto;

public class RegistrationDto {
    private String displayName;
    private String userName;
    private String password;

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegistrationDto(String displayName, String userName, String password) {
        this.displayName = displayName;
        this.userName = userName;
        this.password = password;
    }
}
