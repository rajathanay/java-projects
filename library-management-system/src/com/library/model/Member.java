package com.library.model;

public class Member extends Person {

    private int borrowedBookCount;

    private final int borrowingLimit = 3;

    public Member(
            int memberId,
            String name
    ) {

        super(memberId, name);

        this.borrowedBookCount = 0;
    }

    public int getMemberId() {
        return getId();
    }

    public int getBorrowedBookCount() {
        return borrowedBookCount;
    }

    public int getBorrowingLimit() {
        return borrowingLimit;
    }

    public boolean canBorrowBook() {

        return borrowedBookCount
                < borrowingLimit;
    }

    public void incrementBorrowedBookCount() {
        borrowedBookCount++;
    }

    public void decrementBorrowedBookCount() {

        if (borrowedBookCount > 0) {
            borrowedBookCount--;
        }
    }

    @Override
public void displayDetails() {

    System.out.println("Member ID: " + getMemberId());
    System.out.println("Member Name: " + getName());

    System.out.println(
            "Books Borrowed: "
            + borrowedBookCount
    );

    System.out.println(
            "Borrowing Limit: "
            + borrowingLimit
    );
}
}