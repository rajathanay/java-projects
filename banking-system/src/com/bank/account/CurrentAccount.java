package com.bank.account;

import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAmountException;

public class CurrentAccount extends Account {

    private final double overdraftLimit = 1000.00;

    // Fixed transaction charge
    private final double transactionCharge = 10.00;


    public CurrentAccount(
            String accountHolderName,
            double balance
    ) {

        super(
                accountHolderName,
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


        double totalDeduction =
                amount + transactionCharge;


        if (
                totalDeduction
                > balance + overdraftLimit
        ) {

            throw new InsufficientBalanceException(
                    "Withdrawal denied. Overdraft limit exceeded."
            );
        }


        balance -= totalDeduction;


        System.out.printf(
                "Withdrawal amount: $%.2f%n",
                amount
        );


        System.out.printf(
                "Transaction charge: $%.2f%n",
                transactionCharge
        );


        System.out.printf(
                "New balance: $%.2f%n",
                balance
        );
    }
}