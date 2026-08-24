package com.bank.account;

import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAmountException;

public class SavingsAccount extends Account {

    private final double minimumBalance = 500.00;

    // Same interest rate shared by all savings accounts
    private static double interestRate = 4.0;


    public SavingsAccount(
            String accountHolderName,
            double balance
    ) {

        super(
                accountHolderName,
                balance
        );
    }


    public static double getInterestRate() {
        return interestRate;
    }


    public static void setInterestRate(double newRate) {

        if (newRate < 0) {

            throw new InvalidAmountException(
                    "Interest rate cannot be negative."
            );
        }

        interestRate = newRate;
    }


    public void addInterest() {

        double interest =
                balance * interestRate / 100;

        balance += interest;

        System.out.printf(
                "Interest of $%.2f added.%n",
                interest
        );

        System.out.printf(
                "New balance: $%.2f%n",
                balance
        );
    }


    @Override
    public void withdraw(double amount)
            throws InsufficientBalanceException {

        if (amount <= 0) {

            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero."
            );
        }


        if (balance - amount < minimumBalance) {

            throw new InsufficientBalanceException(
                    "Minimum balance of $"
                    + minimumBalance
                    + " must be maintained."
            );
        }


        balance -= amount;

        System.out.printf(
                "Withdrawal successful. New balance: $%.2f%n",
                balance
        );
    }
}