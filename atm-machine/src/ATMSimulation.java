import java.util.Scanner;

public class ATMSimulation {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            int correctPin = 2321;
            boolean authenticated = false;

            double balance = 20000.00;

            final double DAILY_WITHDRAWAL_LIMIT = 5000.00;
            double totalWithdrawnToday = 0.00;

            String[] transactions = new String[10];
            int transactionCount = 0;


            System.out.println("=========================");
            System.out.println("       ATM MACHINE");
            System.out.println("=========================");


            // PIN Validation

            for (int attempt = 1; attempt <= 3; attempt++) {

                System.out.print("Enter your PIN: ");
                int enteredPin = scanner.nextInt();

                if (enteredPin == correctPin) {

                    authenticated = true;

                    System.out.println(
                            "\nPIN verified successfully."
                    );

                    break;

                } else {

                    System.out.println("Incorrect PIN.");

                    System.out.println(
                            "Attempts remaining: "
                            + (3 - attempt)
                    );
                }
            }


            // After 3 incorrect attempts stop
            if (!authenticated) {

                System.out.println(
                        "\nToo many incorrect attempts. Account locked."
                );

                return;
            }


            // ATM Menu

            boolean running = true;

            while (running) {

                System.out.println("\n=========================");
                System.out.println("ATM MENU");
                System.out.println("=========================");

                System.out.println("1. Check Balance");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Mini Statement");
                System.out.println("5. Exit");

                System.out.print("\nChoose an option: ");

                int choice = scanner.nextInt();


                switch (choice) {
                    // Balance Check
                    case 1 -> System.out.printf(
                            "Current Balance: $%.2f%n",
                            balance
                    );

                    // Deposit
                    case 2 -> {
                        System.out.print(
                                "Enter amount to deposit: $"
                        );

                        double depositAmount =
                                scanner.nextDouble();


                        if (depositAmount > 0) {

                            balance += depositAmount;


                            // Save transaction

                            if (
                                    transactionCount
                                    < transactions.length
                            ) {

                                transactions[transactionCount] =
                                        "Deposit: +$"
                                        + String.format(
                                                "%.2f",
                                                depositAmount
                                        );

                                transactionCount++;
                            }


                            System.out.printf(
                                    "Deposit successful.%n"
                                    + "New Balance: $%.2f%n",
                                    balance
                            );

                        } else {

                            System.out.println(
                                    "Deposit amount must be greater than zero."
                            );
                        }
                    }

                    // WITHDRAW + DAILY LIMIT
                    case 3 -> {
                        System.out.print(
                                "Enter amount to withdraw: $"
                        );

                        double withdrawAmount =
                                scanner.nextDouble();


                        // Invalid withdrawal amount

                        if (withdrawAmount <= 0) {

                            System.out.println(
                                    "Withdrawal amount must be greater than zero."
                            );


                        // Not enough money

                        } else if (
                                withdrawAmount > balance
                        ) {

                            System.out.println(
                                    "Insufficient balance."
                            );


                        // Daily withdrawal limit check

                        } else if (
                                totalWithdrawnToday
                                + withdrawAmount
                                > DAILY_WITHDRAWAL_LIMIT
                        ) {

                            double remainingLimit =
                                    DAILY_WITHDRAWAL_LIMIT
                                    - totalWithdrawnToday;


                            System.out.printf(
                                    "Daily withdrawal limit exceeded.%n"
                                    + "Remaining withdrawal limit: $%.2f%n",
                                    remainingLimit
                            );


                        } else {

                            // Deduct money
                            balance -= withdrawAmount;

                            // Update today's withdrawal amount
                            totalWithdrawnToday +=
                                    withdrawAmount;


                            // Save transaction
                            if (
                                    transactionCount
                                    < transactions.length
                            ) {

                                transactions[transactionCount] =
                                        "Withdrawal: -$"
                                        + String.format(
                                                "%.2f",
                                                withdrawAmount
                                        );

                                transactionCount++;
                            }


                            System.out.printf(
                                    "Withdrawal successful.%n"
                                    + "New Balance: $%.2f%n",
                                    balance
                            );


                            double remainingLimit =
                                    DAILY_WITHDRAWAL_LIMIT
                                    - totalWithdrawnToday;


                            System.out.printf(
                                    "Remaining Daily Withdrawal Limit: $%.2f%n",
                                    remainingLimit
                            );
                        }
                    }

                    // MINI STATEMENT
                    case 4 -> {
                        System.out.println(
                                "\n========================="
                        );

                        System.out.println(
                                "      MINI STATEMENT"
                        );

                        System.out.println(
                                "========================="
                        );


                        if (transactionCount == 0) {

                            System.out.println(
                                    "No transactions available."
                            );

                        } else {

                            for (
                                    int i = 0;
                                    i < transactionCount;
                                    i++
                            ) {

                                System.out.println(
                                        (i + 1)
                                        + ". "
                                        + transactions[i]
                                );
                            }
                        }


                        System.out.println(
                                "-------------------------"
                        );


                        System.out.printf(
                                "Current Balance: $%.2f%n",
                                balance
                        );


                        System.out.printf(
                                "Total Withdrawn Today: $%.2f%n",
                                totalWithdrawnToday
                        );


                        System.out.printf(
                                "Remaining Withdrawal Limit: $%.2f%n",
                                DAILY_WITHDRAWAL_LIMIT
                                - totalWithdrawnToday
                        );
                    }

                    // EXIT
                    case 5 -> {
                        System.out.println(
                                "\nThank you for using the ATM."
                        );

                        running = false;
                    }

                    // INVALID MENU OPTION
                    default -> System.out.println(
                            "Invalid option. Please choose 1 to 5."
                    );
                }
            }
        }
    }
}