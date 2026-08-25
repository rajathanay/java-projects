package com.library.interfaces;

import com.library.exception.BookNotAvailableException;
import com.library.exception.MemberLimitExceededException;

public interface Borrowable {

    void borrowBook(
            int bookId,
            int memberId
    )
            throws BookNotAvailableException,
            MemberLimitExceededException;


    void returnBook(
            int bookId,
            int memberId
    );
}