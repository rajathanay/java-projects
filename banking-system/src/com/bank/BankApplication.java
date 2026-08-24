package com.bank;

import com.bank.account.Account;
import com.bank.account.CurrentAccount;
import com.bank.account.SavingsAccount;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAmountException;
import com.bank.util.BankService;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankApplication {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            BankService bankService =
                    new BankService(100);

            boolean running = true;


            System.out.println(
                    "================================"
            );

            System.out.println(
                    "       " + Account.getBankName()
            );

            System.out.println(
                    "================================"
            );


            while (running) {

                System.out.println(
                        "\n========== BANK MENU =========="
                );

                System.out.println(
                        "1. Create Savings Account"
                );

                System.out.println(
                        "2. Create Current Account"
                );

                System.out.println(
                        "3. Deposit"
                );

                System.out.println(
                        "4. Withdraw"
                );

                System.out.println(
                        "5. Transfer"
                );

                System.out.println(
                        "6. View Account"
                );

                System.out.println(
                        "7. View All Accounts"
                );

                System.out.println(
                        "8. Exit"
                );


                System.out.print(
                        "\nChoose an option: "
                );


                int choice;

                try {

                    choice = scanner.nextInt();

                } catch (InputMismatchException e) {

                    System.out.println(
                            "Please enter a number from 1 to 8."
                    );

                    scanner.nextLine();

                    continue;
                }


                scanner.nextLine();


                switch (choice) {


                    case 1:

                        try {

                            System.out.println(
                                    "\n===== CREATE SAVINGS ACCOUNT ====="
                            );


                            System.out.print(
                                    "Enter account holder name: "
                            );

                            String savingsName =
                                    scanner.nextLine();


                            System.out.print(
                                    "Enter initial balance: $"
                            );

                            double savingsBalance =
                                    scanner.nextDouble();

                            scanner.nextLine();


                            SavingsAccount savings =
                                    new SavingsAccount(
                                            savingsName,
                                            savingsBalance
                                    );


                            bankService.addAccount(
                                    savings
                            );


                            System.out.println(
                                    "\nSavings account created successfully."
                            );


                            System.out.println(
                                    "Your account number is: "
                                    + savings.getAccountNumber()
                            );


                        } catch (
                                InvalidAmountException e
                        ) {

                            System.out.println(
                                    "Error: "
                                    + e.getMessage()
                            );

                        } catch (
                                InputMismatchException e
                        ) {

                            System.out.println(
                                    "Invalid balance."
                            );

                            scanner.nextLine();
                        }

                        break;

                    case 2:

                        try {

                            System.out.println(
                                    "\n===== CREATE CURRENT ACCOUNT ====="
                            );


                            System.out.print(
                                    "Enter account holder name: "
                            );

                            String currentName =
                                    scanner.nextLine();


                            System.out.print(
                                    "Enter initial balance: $"
                            );

                            double currentBalance =
                                    scanner.nextDouble();

                            scanner.nextLine();


                            CurrentAccount current =
                                    new CurrentAccount(
                                            currentName,
                                            currentBalance
                                    );


                            bankService.addAccount(
                                    current
                            );


                            System.out.println(
                                    "\nCurrent account created successfully."
                            );


                            System.out.println(
                                    "Your account number is: "
                                    + current.getAccountNumber()
                            );


                        } catch (
                                InvalidAmountException e
                        ) {

                            System.out.println(
                                    "Error: "
                                    + e.getMessage()
                            );

                        } catch (
                                InputMismatchException e
                        ) {

                            System.out.println(
                                    "Invalid balance."
                            );

                            scanner.nextLine();
                        }

                        break;



                    case 3:

                        try {

                            System.out.println(
                                    "\n===== DEPOSIT ====="
                            );


                            System.out.print(
                                    "Enter account number: "
                            );

                            int depositAccountNumber =
                                    scanner.nextInt();


                            System.out.print(
                                    "Enter deposit amount: $"
                            );

                            double depositAmount =
                                    scanner.nextDouble();

                            scanner.nextLine();


                            bankService.deposit(
                                    depositAccountNumber,
                                    depositAmount
                            );


                        } catch (
                                AccountNotFoundException e
                        ) {

                            System.out.println(
                                    "Error: "
                                    + e.getMessage()
                            );

                        } catch (
                                InvalidAmountException e
                        ) {

                            System.out.println(
                                    "Error: "
                                    + e.getMessage()
                            );

                        } catch (
                                InputMismatchException e
                        ) {

                            System.out.println(
                                    "Invalid input."
                            );

                            scanner.nextLine();
                        }

                        break;



                    case 4:

                        try {

                            System.out.println(
                                    "\n===== WITHDRAW ====="
                            );


                            System.out.print(
                                    "Enter account number: "
                            );

                            int withdrawAccountNumber =
                                    scanner.nextInt();


                            System.out.print(
                                    "Enter withdrawal amount: $"
                            );

                            double withdrawAmount =
                                    scanner.nextDouble();

                            scanner.nextLine();


                            bankService.withdraw(
                                    withdrawAccountNumber,
                                    withdrawAmount
                            );


                        } catch (
                                AccountNotFoundException e
                        ) {

                            System.out.println(
                                    "Error: "
                                    + e.getMessage()
                            );

                        } catch (
                                InvalidAmountException e
                        ) {

                            System.out.println(
                                    "Error: "
                                    + e.getMessage()
                            );

                        } catch (
                                InsufficientBalanceException e
                        ) {

                            System.out.println(
                                    "Balance Error: "
                                    + e.getMessage()
                            );

                        } catch (
                                InputMismatchException e
                        ) {

                            System.out.println(
                                    "Invalid input."
                            );

                            scanner.nextLine();
                        }

                        break;


                    // ==================================
                    // TRANSFER
                    // ==================================

                    case 5:

                        try {

                            System.out.println(
                                    "\n===== TRANSFER ====="
                            );


                            System.out.print(
                                    "Enter source account number: "
                            );

                            int fromAccount =
                                    scanner.nextInt();


                            System.out.print(
                                    "Enter destination account number: "
                            );

                            int toAccount =
                                    scanner.nextInt();


                            System.out.print(
                                    "Enter transfer amount: $"
                            );

                            double transferAmount =
                                    scanner.nextDouble();

                            scanner.nextLine();


                            bankService.transfer(
                                    fromAccount,
                                    toAccount,
                                    transferAmount
                            );


                        } catch (
                                AccountNotFoundException e
                        ) {

                            System.out.println(
                                    "Error: "
                                    + e.getMessage()
                            );

                        } catch (
                                InvalidAmountException e
                        ) {

                            System.out.println(
                                    "Error: "
                                    + e.getMessage()
                            );

                        } catch (
                                InsufficientBalanceException e
                        ) {

                            System.out.println(
                                    "Balance Error: "
                                    + e.getMessage()
                            );

                        } catch (
                                IllegalArgumentException e
                        ) {

                            System.out.println(
                                    "Transfer Error: "
                                    + e.getMessage()
                            );

                        } catch (
                                InputMismatchException e
                        ) {

                            System.out.println(
                                    "Invalid input."
                            );

                            scanner.nextLine();
                        }

                        break;


                    // ==================================
                    // VIEW ACCOUNT
                    // ==================================

                    case 6:

                        try {

                            System.out.print(
                                    "Enter account number: "
                            );

                            int accountNumber =
                                    scanner.nextInt();

                            scanner.nextLine();


                            Account account =
                                    bankService.findAccount(
                                            accountNumber
                                    );


                            System.out.println(
                                    "\n===== ACCOUNT DETAILS ====="
                            );


                            account.displayAccountDetails();


                        } catch (
                                AccountNotFoundException e
                        ) {

                            System.out.println(
                                    "Error: "
                                    + e.getMessage()
                            );

                        } catch (
                                InputMismatchException e
                        ) {

                            System.out.println(
                                    "Invalid account number."
                            );

                            scanner.nextLine();
                        }

                        break;



                    case 7:

                        bankService.displayAllAccounts();

                        break;



                    case 8:

                        System.out.println(
                                "\nThank you for using "
                                + Account.getBankName()
                                + "."
                        );

                        running = false;

                        break;


                    default:

                        System.out.println(
                                "Invalid option. Choose 1 to 8."
                        );
                }
            }
        }
    }
}