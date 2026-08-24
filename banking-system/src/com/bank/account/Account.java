package com.bank.account;

import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAmountException;

public abstract class Account {

    private static String bankName = "ABC Bank";

    private static int accountCounter = 1000;

    protected final int accountNumber;

    private String accountHolderName;

    protected double balance;


    public Account(
            String accountHolderName,
            double balance
    ) {

        if (balance < 0) {

            throw new InvalidAmountException(
                    "Initial balance cannot be negative."
            );
        }

        accountCounter++;

        this.accountNumber = accountCounter;

        this.accountHolderName = accountHolderName;

        this.balance = balance;
    }


    public static String getBankName() {
        return bankName;
    }


    public int getAccountNumber() {
        return accountNumber;
    }


    public String getAccountHolderName() {
        return accountHolderName;
    }


    public double getBalance() {
        return balance;
    }


    public void deposit(double amount) {

        if (amount <= 0) {

            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero."
            );
        }

        balance += amount;

        System.out.printf(
                "Deposit successful. New balance: $%.2f%n",
                balance
        );
    }


    public abstract void withdraw(double amount)
            throws InsufficientBalanceException;


    public void displayAccountDetails() {

        System.out.println("------------------------------");

        System.out.println(
                "Bank: " + bankName
        );

        System.out.println(
                "Account Number: " + accountNumber
        );

        System.out.println(
                "Account Holder: " + accountHolderName
        );

        System.out.println(
                "Account Type: "
                + getClass().getSimpleName()
        );

        System.out.printf(
                "Balance: $%.2f%n",
                balance
        );

        System.out.println("------------------------------");
    }
}