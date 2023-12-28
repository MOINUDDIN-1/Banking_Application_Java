package com.bank.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transactions {
    private String transaction_id;
    private int account_holder,beneficiary_id;
    private double transferred_amount;
    private String transaction_narration;
    private Date transaction_date;
    private String transaction_status;

    public Transactions(String transaction_id, int account_holder, int beneficiary_id, double transferred_amount, String transaction_narration, Date transaction_date, String transaction_status) {
        this.transaction_id = transaction_id;
        this.account_holder = account_holder;
        this.beneficiary_id = beneficiary_id;
        this.transferred_amount = transferred_amount;
        this.transaction_narration = transaction_narration;
        this.transaction_date = transaction_date;
        this.transaction_status = transaction_status;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getAccount_holder() {
        return account_holder;
    }

    public void setAccount_holder(int account_holder) {
        this.account_holder = account_holder;
    }

    public int getBeneficiary_id() {
        return beneficiary_id;
    }

    public void setBeneficiary_id(int beneficiary_id) {
        this.beneficiary_id = beneficiary_id;
    }

    public double getTransferred_amount() {
        return transferred_amount;
    }

    public void setTransferred_amount(double transferred_amount) {
        this.transferred_amount = transferred_amount;
    }

    public String getTransaction_narration() {
        return transaction_narration;
    }

    public void setTransaction_narration(String transaction_narration) {
        this.transaction_narration = transaction_narration;
    }

    public Date getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(Date transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public Transactions() {
    }

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  formatString(transaction_id ,account_holder ,transferred_amount ,transaction_narration ,(transaction_date != null ? dateFormat.format(transaction_date) : "N/A") ,transaction_status);
    }
    public static String formatString(String transaction_id, int account_holder, double transferred_amount, String transaction_narration, String transaction_date, String transaction_status) {
        // Use format specifiers to control spacing and alignment
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedString = String.format("%-14s | %-17s | %-6.2f | %-20s | %-12s | %-7s",
                transaction_id, account_holder, transferred_amount, transaction_narration,
                transaction_date, transaction_status);

        return formattedString;
    }
}
