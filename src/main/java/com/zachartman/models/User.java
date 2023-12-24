package com.zachartman.models;

public abstract class User {
    private final String fullName;
    private final String username;
    protected String password;
    public User(String fullName, String username, String password) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {return password;}
}
