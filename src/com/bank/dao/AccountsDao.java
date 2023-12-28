package com.bank.dao;

import com.bank.model.Accounts;
import com.bank.model.Beneficiaries;
import com.bank.model.Transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AccountsDao {

    String jdbcUrl = "jdbc:mysql://localhost:3306/account";
    String userName = "root";
    String passwordField = "root@12345";
    Connection connection = null;

    public void connectionOpen() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, userName, passwordField);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public void connectionClose() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public int  createNewAccount(Accounts newAccount) {
        connectionOpen();

        String query = "INSERT INTO account_holder(username, password, account_number, email, account_balance, address) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newAccount.getName());
            preparedStatement.setString(2, newAccount.getPassword());
            preparedStatement.setString(3, newAccount.getAccountNumber());
            preparedStatement.setString(4, newAccount.getEmail());
            preparedStatement.setDouble(5, newAccount.getAccountBalance());
            preparedStatement.setString(6, newAccount.getAddress());

            // Use executeUpdate() instead of executeQuery() for INSERT statements
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Account created successfully.");
            } else {
                System.out.println("Failed to create account.");
            }
            return getNewCustomerId();





        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            connectionClose();
        }
       return -1;
    }
    public int getNewCustomerId() {
        int newCustomerId = -1; // Default value in case of an issue

        connectionOpen();

        String query2 = "SELECT MAX(customer_id) AS new_customer_id FROM account_holder;";
        try {
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            ResultSet resultSet2 = preparedStatement2.executeQuery();

            if (resultSet2.next()) {
                newCustomerId = resultSet2.getInt("new_customer_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            connectionClose();
        }

        return newCustomerId;
    }


    public boolean userLogin(int customerId,String password){
        connectionOpen();

        String query = "SELECT * FROM account_holder WHERE customer_id = ? AND password = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Set parameters for the PreparedStatement as needed
            preparedStatement.setInt(1,customerId);
            preparedStatement.setString(2,password);
            ResultSet resultSet=preparedStatement.executeQuery();
//            System.out.println(resultSet);
            if (resultSet.next()) {
                return true;
                // Process the ResultSet or retrieve specific values if needed
            } else {
                return false;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        connectionClose();
        return false;

    }


//    public List<Beneficiaries> fetchAllBeneficiaries(int loggedInUser) {
//        connectionOpen();
//
//        String query = "select * from beneficiary where account_holder_id =?;";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            // Process the result set as needed
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Handle the exception appropriately
//        }
//
//        connectionClose();
//        return null;
//    }

    public List<Beneficiaries> fetchAllBeneficiaries(int customerId) {

        connectionOpen();
        List<Beneficiaries> beneficiariesList = new ArrayList<>();

        String query = "select * from beneficiary where account_holder_id =?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Beneficiaries beneficiaries = new Beneficiaries();
                beneficiaries.setBeneficiary_id(resultSet.getInt(1));
                beneficiaries.setAccount_holder_id(resultSet.getInt(2));
                beneficiaries.setName(resultSet.getString(3));
                beneficiaries.setAccount_number(resultSet.getString(4));
                beneficiaries.setAccount_status(resultSet.getString(5));
                beneficiaries.setIfsc_code(resultSet.getString(6));
                beneficiaries.setEmail(resultSet.getString(7));
                beneficiaries.setMax_transfer_amount(resultSet.getDouble(8));
                beneficiariesList.add(beneficiaries);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            connectionClose();
        }

        return beneficiariesList;
    }

    public int addNewBeneficiary(Beneficiaries beneficiaries) {
        connectionOpen();

        String query = "INSERT INTO beneficiary(account_holder_id, name, account_number, account_status, ifsc_code, email, max_transfer_amount) VALUES (?, ?, ?, ?, ?, ?, ?)";

        int rowsAffected = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, beneficiaries.getAccount_holder_id());
            preparedStatement.setString(2, beneficiaries.getName());
            preparedStatement.setString(3, beneficiaries.getAccount_number());
            preparedStatement.setString(4, beneficiaries.getAccount_status());
            preparedStatement.setString(5, beneficiaries.getIfsc_code());
            preparedStatement.setString(6, beneficiaries.getEmail());
            preparedStatement.setDouble(7, beneficiaries.getMax_transfer_amount());

            // Execute the update and get the number of rows affected
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            connectionClose();
        }

        return rowsAffected;
    }



    public double fetchBalance(int customerId) { // done
        double balance = 0.0;
        connectionOpen();

        String query = "SELECT account_balance FROM account_holder WHERE customer_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                balance = resultSet.getDouble("account_balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        connectionClose();
        return balance;
    }
    public List<Transactions> fetchAllTransactions(int customerId) {

        connectionOpen();
        List<Transactions> transactionsList = new ArrayList<>();

        String query = "SELECT * FROM transaction WHERE account_holder_id = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Transactions transactions = new Transactions();
                transactions.setTransaction_id(resultSet.getString(1));
                transactions.setAccount_holder(resultSet.getInt(2));
                transactions.setBeneficiary_id(resultSet.getInt(3));
                transactions.setTransferred_amount(resultSet.getDouble(4));
                transactions.setTransaction_narration(resultSet.getString(5));
                transactions.setTransaction_date(resultSet.getDate(6));
                transactions.setTransaction_status(resultSet.getString(7));
                transactionsList.add(transactions);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            connectionClose();
        }

        return transactionsList;
    }
    public boolean addRandomAmount(int customerId, double amount) {
        connectionOpen();

        String query = "UPDATE account_holder SET account_balance = account_balance + ? WHERE customer_id = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, customerId);

            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the update was successful by checking the number of affected rows
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            connectionClose();
        }

        return false;
    }


    public int sendMoneyToBeneficiary(int accountHolderId, int beneficiaryId, double amount, String narration,String status) {
        connectionOpen();

        int rowsAffected = 0;

        try {
            // Start transaction
            connection.setAutoCommit(false);


            if(!Objects.equals(status, "failed")){
                // Update account_holder
                String updateAccountHolderQuery = "UPDATE account_holder SET account_balance = account_balance - ? WHERE customer_id = ?";
                PreparedStatement updateAccountHolderStatement = connection.prepareStatement(updateAccountHolderQuery);
                updateAccountHolderStatement.setDouble(1, amount);
                updateAccountHolderStatement.setInt(2, accountHolderId);
                rowsAffected += updateAccountHolderStatement.executeUpdate();
//            System.out.println("done 1");
            }


//            // Update beneficiary
//            String updateBeneficiaryQuery = "UPDATE beneficiary SET account_balance = account_balance + ? WHERE beneficiary_id = ?";
//            PreparedStatement updateBeneficiaryStatement = connection.prepareStatement(updateBeneficiaryQuery);
//            updateBeneficiaryStatement.setDouble(1, amount);
//            updateBeneficiaryStatement.setInt(2, beneficiaryId);
//            rowsAffected += updateBeneficiaryStatement.executeUpdate();
//            System.out.println("done 2");

            // Insert transaction
            String insertTransactionQuery = "INSERT INTO transaction (transaction_id, account_holder_id, beneficiary_id, transferred_amount, transaction_narration, transaction_status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertTransactionStatement = connection.prepareStatement(insertTransactionQuery);
            insertTransactionStatement.setString(1, createTransactionId());
            insertTransactionStatement.setInt(2, accountHolderId);
            insertTransactionStatement.setInt(3, beneficiaryId);
            insertTransactionStatement.setDouble(4, amount);
            insertTransactionStatement.setString(5, narration);
            insertTransactionStatement.setString(6, status);
            rowsAffected += insertTransactionStatement.executeUpdate();
//            System.out.println("done 3");

            // Commit the transaction
            connection.commit();

        } catch (SQLException e) {
            // Rollback the transaction in case of an exception
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            // Reset auto-commit to true
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitException) {
                autoCommitException.printStackTrace();
            }

            connectionClose();
        }

        return rowsAffected;
    }


    public String createTransactionId(){
        int sequentialNumber=0;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Format the date and time parts
        String datePart = String.format("%02d%02d%04d%02d%02d", day, month, year, hour, minute);

        // Format the sequential number part (xxx)
        String sequentialNumberPart = String.format("%03d", sequentialNumber++);

        // Combine the date and sequential number parts
        return datePart + sequentialNumberPart;
    }
}
