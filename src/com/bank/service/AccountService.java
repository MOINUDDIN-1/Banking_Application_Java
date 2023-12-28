package com.bank.service;

import com.bank.dao.AccountsDao;
import com.bank.model.Accounts;
import com.bank.model.Beneficiaries;
import com.bank.model.Transactions;

import java.util.List;

public class AccountService {
    Accounts accounts;

    public AccountService(Accounts accounts) {
        this.accounts = accounts;
    }

    public AccountService() {

    }

    List<Transactions> transactions;
    AccountsDao accountsDao=new AccountsDao();
    public List<Transactions> fetchAllTransactions(int customerId){
        return accountsDao.fetchAllTransactions(customerId);
    }
    public List<Beneficiaries> sendMoney(int customerId,int beneficiaryId,Double amount){

        return null;
    }
    public List<Beneficiaries> fetchAllBeneficiaries(int loggedInUser){
        return accountsDao.fetchAllBeneficiaries(loggedInUser);
    }

    public boolean userLogin(int customerId,String password){
        return accountsDao.userLogin(customerId,password);
    }
    public double fetchBalance(int customerId) {
        return accountsDao.fetchBalance(customerId);
    }
    public boolean addRandomAmount(int customerId,double amount){
        return accountsDao.addRandomAmount(customerId,amount);
    }

    public int createNewAccount(Accounts newAccount){
        return accountsDao.createNewAccount(newAccount);

    }


    public int addNewBeneficiary(Beneficiaries beneficiaries) {
        return accountsDao.addNewBeneficiary(beneficiaries);
    }

    public int  sendMoneyToBeneficiary(int account_holder_id,int beneficiary_id,double amount,String narration,String status) {
        return accountsDao. sendMoneyToBeneficiary(account_holder_id,beneficiary_id,amount,narration,status);
    }
}
