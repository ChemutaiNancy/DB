package com.chemutai.sqlite;

public class Customer {
    private long id;
    private String names, email;
    private double balance;

    public Customer() {
    }//empty constructor

    public Customer(long id, String names, String email, double balance) {
        this.id = id;
        this.names = names;
        this.email = email;
        this.balance = balance;
    }

    public Customer(String names, String email, double balance) {
        this.names = names;
        this.email = email;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
