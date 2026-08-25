package com.library.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transaction {

    private final int transactionId;

    private final int bookId;
    private final int memberId;

    private final LocalDate borrowDate;
    private final LocalDate dueDate;

    private LocalDate returnDate;

    private boolean returned;

    private double fine;

    public Transaction(
            int transactionId,
            int bookId,
            int memberId
    ) {

        this.transactionId = transactionId;
        this.bookId = bookId;
        this.memberId = memberId;

        this.borrowDate = LocalDate.now();

        this.dueDate =
                borrowDate.plusDays(14);

        this.returnDate = null;

        this.returned = false;

        this.fine = 0.0;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public double getFine() {
        return fine;
    }

   public void completeReturn() {

    if (returned) {
        System.out.println("Book already returned.");
        return;
    }

    returnDate = LocalDate.now();

    FineCalculator fineCalculator =
            new FineCalculator();

    fine = fineCalculator.calculateFine(
            dueDate,
            returnDate
    );

    returned = true;
}

    public void displayTransactionDetails() {

        System.out.println("----------------------------");

        System.out.println(
                "Transaction ID: "
                + transactionId
        );

        System.out.println(
                "Book ID: " + bookId
        );

        System.out.println(
                "Member ID: " + memberId
        );

        System.out.println(
                "Borrow Date: " + borrowDate
        );

        System.out.println(
                "Due Date: " + dueDate
        );

        if (returned) {

            System.out.println(
                    "Return Date: "
                    + returnDate
            );

            System.out.printf(
                    "Fine: $%.2f%n",
                    fine
            );

        } else {

            System.out.println(
                    "Status: Not Returned"
            );
        }

        System.out.println("----------------------------");
    }


    // Required inner class
    public class FineCalculator {

    private final double finePerDay = 2.0;

    public double calculateFine(
            LocalDate dueDate,
            LocalDate returnDate
    ) {

        if (!returnDate.isAfter(dueDate)) {
            return 0;
        }

        long lateDays =
                ChronoUnit.DAYS.between(
                        dueDate,
                        returnDate
                );

        return lateDays * finePerDay;
    }
}
}