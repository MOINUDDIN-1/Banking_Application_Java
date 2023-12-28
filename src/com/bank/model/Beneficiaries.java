
package com.bank.model;

import java.text.SimpleDateFormat;

public class Beneficiaries {
    private int beneficiary_id,account_holder_id;
    private String name;
    private String account_number;
    private String account_status;
    private String ifsc_code;
    private String email;
    private Double max_transfer_amount;

    public Beneficiaries() {
    }
    public Beneficiaries(int beneficiary_id, int account_holder_id, String name, String account_number, String account_status, String ifsc_code, String email, Double max_transfer_amount) {
        this.beneficiary_id = beneficiary_id;
        this.account_holder_id = account_holder_id;
        this.name = name;
        this.account_number = account_number;
        this.account_status = account_status;
        this.ifsc_code = ifsc_code;
        this.email = email;
        this.max_transfer_amount = max_transfer_amount;
    }



    public int getBeneficiary_id() {
        return beneficiary_id;
    }

    public void setBeneficiary_id(int beneficiary_id) {
        this.beneficiary_id = beneficiary_id;
    }

    public int getAccount_holder_id() {
        return account_holder_id;
    }

    public void setAccount_holder_id(int account_holder_id) {
        this.account_holder_id = account_holder_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_status() {
        return account_status;
    }

    public void setAccount_status(String account_status) {
        this.account_status = account_status;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Double getMax_transfer_amount() {
        return max_transfer_amount;
    }

    public void setMax_transfer_amount(Double max_transfer_amount) {
        this.max_transfer_amount = max_transfer_amount;
    }

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  formatString(beneficiary_id,name ,manipulateString(account_number) ,account_status , ifsc_code, email,max_transfer_amount);
    }
    public static String formatString(int beneficiaryId, String name, String accountNumber,
                                        String accountStatus, String ifscCode, String email,
                                        double maxTransferAmount) {
        // Use format specifiers to control spacing and alignment
        String formattedString = String.format("%-15s | %-4s | %-15s | %-13s | %-9s | %-17s | %.2f",
                beneficiaryId, name, manipulateString(accountNumber), accountStatus, ifscCode, email, maxTransferAmount);

//        System.out.println(formattedString);
        return formattedString;
    }


    public static String manipulateString(String input) {
        if (input.length() < 4) {
            // Handle strings with less than 4 characters (if needed)
            return input;
        }

        // Extract first letter
        String firstLetter = input.substring(0, 1);

        // Extract last three letters
        String lastThreeLetters = input.substring(input.length() - 3);

        // Concatenate the parts
        return firstLetter + "xxx" + lastThreeLetters;
    }
}
