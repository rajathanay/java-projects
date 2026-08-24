package com.bank.util;
import com.bank.account.Account;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientBalanceException;

public class BankService {

    private final Account[] accounts;

    private int accountCount;


    public BankService(int maximumAccounts) {

        accounts = new Account[maximumAccounts];

        accountCount = 0;
    }


    // Add a new account
    public void addAccount(Account account) {

        if (accountCount < accounts.length) {

            accounts[accountCount] = account;

            accountCount++;

        } else {

            System.out.println(
                    "Bank account storage is full."
            );
        }
    }


    // Find account using account number
    public Account findAccount(int accountNumber)
            throws AccountNotFoundException {

        for (int i = 0; i < accountCount; i++) {

            if (
                    accounts[i].getAccountNumber()
                    == accountNumber
            ) {

                return accounts[i];
            }
        }


        throw new AccountNotFoundException(
                "Account "
                + accountNumber
                + " was not found."
        );
    }


    // Deposit money
    public void deposit(
            int accountNumber,
            double amount
    ) throws AccountNotFoundException {

        Account account =
                findAccount(accountNumber);

        account.deposit(amount);
    }


    // Withdraw money
    public void withdraw(
            int accountNumber,
            double amount
    )
            throws AccountNotFoundException,
            InsufficientBalanceException {

        Account account =
                findAccount(accountNumber);

        account.withdraw(amount);
    }


    // Transfer money
    public void transfer(
            int fromAccountNumber,
            int toAccountNumber,
            double amount
    )
            throws AccountNotFoundException,
            InsufficientBalanceException {

        // Find both accounts before modifying balances
        Account fromAccount =
                findAccount(fromAccountNumber);

        Account toAccount =
                findAccount(toAccountNumber);


        if (fromAccountNumber == toAccountNumber) {

            throw new IllegalArgumentException(
                    "Source and destination accounts cannot be the same."
            );
        }


        // Withdraw applies account-specific rules
        fromAccount.withdraw(amount);


        // Deposit into destination account
        toAccount.deposit(amount);


        System.out.println(
                "Transfer completed successfully."
        );
    }


    // Display all accounts
    public void displayAllAccounts() {

        if (accountCount == 0) {

            System.out.println(
                    "No accounts available."
            );

            return;
        }


        System.out.println(
                "\n===== ALL ACCOUNTS ====="
        );


        for (int i = 0; i < accountCount; i++) {

            accounts[i].displayAccountDetails();
        }
    }
}