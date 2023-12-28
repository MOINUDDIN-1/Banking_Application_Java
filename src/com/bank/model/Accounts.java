package com.bank.model;

import java.util.Date;

public class Accounts {
    private String name;
    private String password;
    private String accountNumber;
    private String email;
    private String address;
    private int customer_id;
    private Double accountBalance;


    public Accounts() {
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Accounts(int customer_id, String name, String password, String accountNumber, String email,Double accountBalance, String address) {
        this.name = name;
        this.email = email;
        this.accountNumber=accountNumber;
        this.address = address;
        this.customer_id=customer_id;
        this.accountBalance=accountBalance;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
