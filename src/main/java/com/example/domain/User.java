package com.example.domain;

public class User {
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int userId;
    public String username;
    public String password;

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
