package com.bank.main;

import com.bank.exceptions.InsufficientBalanceException;
import com.bank.model.Accounts;
import com.bank.model.Beneficiaries;
import com.bank.model.Transactions;
import com.bank.service.AccountService;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static boolean enterLoop=true;
    public static List<Beneficiaries> allBeneficiaries;
//    Beneficiaries selectedBeneficiary;
    public static void main(String[] args) {
        while(true){
            System.out.println("---------------------------------------------------");
            System.out.println("Welcome to Moin's Bank");
            Scanner scanner=new Scanner(System.in);
            System.out.println("select an option to proceed");
            System.out.println("1:user login. 2:Register New Account. 3:Add random amount to an user 4:My Bank Info");
            int value=scanner.nextInt();
            switch (value){
                case 1: userLogin(); break;
                case 2 : createNewAccount();break;
                case 3 : addingRandomAmount();break;
                case 4 : displayAboutInfo(); break;
                default:
                    System.out.println("select proper option");
                    break;

        }
            System.out.println("---------------------------------------------------");
        }

        //    -----------------------------workspace----------------------------------



//        accountService.fetchAllTransactions(2);
//        double balance =accountService.fetchBalance(1);
//        System.out.println(balance);

        //    -----------------------------workspace----------------------------------


    }
    public static void userLogin(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("enter the details");
        System.out.println("enter customer ID");
        int customerId= scanner.nextInt();
        scanner.nextLine();
        System.out.println("enter the password");
        String password=scanner.nextLine();
        AccountService accountService=new AccountService();
        boolean v1=accountService.userLogin(customerId,password);
        if(v1){
            System.out.println("user logged in successfully");
            enterLoop=true;
            loggedInOperations(customerId);
        }else{
            System.out.println("incorrect account number or password");
        }
        System.out.println("---------------------------------------------------");

    }
    public static void createNewAccount(){
        System.out.println("enter your personal details");
        Scanner scanner=new Scanner(System.in);
        System.out.println("Full Name");
        String userName=scanner.nextLine();
        System.out.println("Account Number");
        String accountNumber=scanner.nextLine();
        System.out.println("email ID");
        String emailId=scanner.nextLine();
//        System.out.println("Date of Birth (dd-mm-yyyy format)");
//        String dob =scanner.nextLine();
        System.out.println("address");
        String address =scanner.nextLine();
        System.out.println("set a password");
        String password=scanner.nextLine();
//        System.out.println(useName+phoneNumber+emailId+dob+address);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");

        Accounts newAccount=new Accounts(0,userName,password,accountNumber,emailId,1000.0,address);
        AccountService accountService=new AccountService(newAccount);
        int newId=accountService.createNewAccount(newAccount);


        if(newId>0){
        System.out.println("new account created successfully");
        System.out.println(userName+ ",your customerId is `"+newId+"`");
        }else {
            System.out.println("new account creation failed");
        }
        System.out.println("---------------------------------------------------");
    }
    public static void addingRandomAmount(){
        System.out.println("enter the customer id");
        Scanner scanner=new Scanner(System.in);
        int customerId =scanner.nextInt();
        scanner.nextLine();
        System.out.println("enter the amount to send (< 50,000)");
        int amount=scanner.nextInt();
//        scanner.nextLine();
//        int status=1;
        if( amount<=50000){
            AccountService accountService=new AccountService();
            accountService.addRandomAmount(customerId,amount);
            System.out.println(amount+"rupees added to customer " + customerId);
        }else {
            System.out.println("couldn't able to add the amount");
        }
//        System.out.println(accountNumber+amount);
        System.out.println("---------------------------------------------------");

    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public static void loggedInOperations(int loggedInUser){
    System.out.println("---------------------------------------------------");
        while(enterLoop){
            System.out.println("select an operation");
//            int loggedInUser=userId;
            System.out.println("1:Send Money 2:Check Balance 3:All transactions 4:logout");
            Scanner scanner=new Scanner(System.in);
            AccountService accountService=new AccountService();
            int value=scanner.nextInt();

                switch(value){
                    case 1: sendMoney(accountService, loggedInUser);break;
                    case 2:double balance=fetchBalance(accountService, loggedInUser);
                        System.out.println("Your account balance is "+ balance);
                        System.out.println("---------------------------------------------------");
                        break;
                    case 3: fetchAndDisplayAllTransactions(loggedInUser);break;
                    case 4:
                        System.out.println("user logged out successfully");
                        enterLoop=false;break;
                    default:System.out.println("Invalid choice");


            }

        }
    System.out.println("---------------------------------------------------");
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void sendMoney(AccountService accountService, int loggedInUser) {
        fetchAllBeneficiaries(accountService,loggedInUser);
        Scanner scanner=new Scanner(System.in);
        int value=scanner.nextInt();
        if(value==0){
            addNewBeneficiary(loggedInUser);
        }else{
            sendMoneyToBeneficiary(accountService,loggedInUser,value);
        }
        System.out.println("---------------------------------------------------");
    }



    public static void fetchAllBeneficiaries(AccountService accountService, int loggedInUser){
        allBeneficiaries=accountService.fetchAllBeneficiaries(loggedInUser);
        System.out.println("---------------------------------------------------");
        System.out.println("---------------------------------------------------");
        System.out.println("select one of the Beneficiary IDs");
        System.out.println("---------------------------------------------------");
        System.out.println("0: Add New Beneficiary");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.println("Beneficiary Id  | Name | Account number | Account status | IFSC code | Email             | Max Transfer Amount");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        for(Beneficiaries beneficiaries:allBeneficiaries){
            System.out.println(beneficiaries);
        }
        System.out.println("--------------------------------------------------------------------------------------------------------");
    }
    private static void addNewBeneficiary(int loggedInUser){
        System.out.println("enter the details to add beneficiary account");
        Scanner scanner=new Scanner(System.in);
        System.out.println("Name");
        String name=scanner.nextLine();
        System.out.println("account_number");
        String accountNumber=scanner.nextLine();
        System.out.println("account_status(active?)- y/n");
        String status=scanner.nextLine();
        if(Objects.equals(status, "y")){
            status="active";
        }else status="inactive";

        System.out.println("IFSC code");
        String ifsc=scanner.nextLine();
        System.out.println("email");
        String email=scanner.nextLine();
        System.out.println("maximum transfer amount");
        Double maxAmount=scanner.nextDouble();
        Beneficiaries beneficiaries=new Beneficiaries(0,loggedInUser,name,accountNumber,status,ifsc,email,maxAmount);
        AccountService accountService=new AccountService();
        int status1 = accountService.addNewBeneficiary(beneficiaries);
        if(status1>0){
            System.out.println("New Beneficiary added successfully");
        }else{
            System.out.println("Failed To add the new Beneficiary");
        }
        System.out.println("---------------------------------------------------");
    }
    private static void sendMoneyToBeneficiary(AccountService accountService,int loggedInUser,int beneficiaryId) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("enter the amount to be transferred");
        double amount=scanner.nextDouble();
        scanner.nextLine();
        System.out.println("enter the message");
        String message=scanner.nextLine();
        String status="completed";
        for(Beneficiaries beneficiaries:allBeneficiaries){
            if (beneficiaries.getBeneficiary_id() == beneficiaryId) {
                // check some of the conditions
                if(fetchBalance(accountService, loggedInUser) < amount ){
                    status = "failed";
                    try {
                        throw new InsufficientBalanceException();
                    } catch (InsufficientBalanceException insufficientBalanceException) {
                        System.out.println("Transaction Failed!");
                        insufficientBalanceException.printStackTrace();
                    }

                }

                if ( !Objects.equals(beneficiaries.getAccount_status(), "active")) {
                    status = "failed";
                    System.out.println("Beneficiary account is dead");
                }
            }

        }

        int value=accountService.sendMoneyToBeneficiary(loggedInUser,beneficiaryId,amount,message,status);
        if(value>0){
            System.out.println("transaction successful");
        }
        System.out.println("---------------------------------------------------");
    }
    private static double fetchBalance(AccountService accountService, int loggedInUser) {
        double balance= accountService.fetchBalance(loggedInUser);
        System.out.println("---------------------------------------------------");
        return balance;
    }
    public static void fetchAndDisplayAllTransactions(int loggedInUser){
            AccountService accountService=new AccountService();
            List<Transactions> list=accountService.fetchAllTransactions(loggedInUser);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("TransactionId   | Beneficiary Name  | Amount | Narration            | Date                | Status " );
        System.out.println("----------------------------------------------------------------------------------------------------");
        for (Transactions transaction : list) {
            System.out.println(transaction);
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }
    public static void displayAboutInfo() {
        System.out.println("About MyBankApp");
        System.out.println("-------------------------------");
        System.out.println("MyBankApp is a simple banking application designed to manage simple transactions.");
        System.out.println("It provides the following features:");
        System.out.println("- Account management: View and add account information.");
        System.out.println("- Transaction handling: Perform transactions like fund transfers.");
        System.out.println("- Beneficiary management: Manage a list of beneficiaries for easy fund transfers.");

        System.out.println("\nKey Information:");
        System.out.println("- Version: 1.0");
        System.out.println("- Developer: `Moinuddin`");
        System.out.println("- Contact: mouddin@altimetrik.com");

        System.out.println("\nThank you for using MyBankApp!");
    }

}
