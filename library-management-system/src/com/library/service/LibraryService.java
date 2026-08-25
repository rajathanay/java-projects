package com.library.service;

import com.library.annotation.Auditable;
import com.library.exception.BookNotAvailableException;
import com.library.exception.BookNotFoundException;
import com.library.exception.MemberLimitExceededException;
import com.library.exception.MemberNotFoundException;
import com.library.interfaces.Borrowable;
import com.library.interfaces.Searchable;
import com.library.model.Book;
import com.library.model.Librarian;
import com.library.model.Member;
import com.library.model.Transaction;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class LibraryService
        implements Searchable, Borrowable {

    private Book[] books;
    private Member[] members;
    private Librarian[] librarians;
    private Transaction[] transactions;

    private int bookCount;
    private int memberCount;
    private int librarianCount;
    private int transactionCount;

    private int nextTransactionId;

    public LibraryService(
            int maximumBooks,
            int maximumMembers
    ) {

        books =
                new Book[maximumBooks];

        members =
                new Member[maximumMembers];

        librarians =
                new Librarian[20];

        transactions =
                new Transaction[500];

        bookCount = 0;
        memberCount = 0;
        librarianCount = 0;
        transactionCount = 0;

        nextTransactionId = 1;
    }


    // ==========================================
    // BOOK
    // ==========================================

    @Auditable(action = "ADD_BOOK")
    public void addBook(Book book) {

        if (bookCount >= books.length) {

            System.out.println(
                    "Book storage is full."
            );

            return;
        }

        books[bookCount] = book;

        bookCount++;

        System.out.println(
                "Book added successfully."
        );
    }


    // ==========================================
    // MEMBER
    // ==========================================

    @Auditable(action = "REGISTER_MEMBER")
    public void registerMember(Member member) {

        if (memberCount >= members.length) {

            System.out.println(
                    "Member storage is full."
            );

            return;
        }

        members[memberCount] = member;

        memberCount++;

        System.out.println(
                "Member registered successfully."
        );
    }


    @Auditable(action = "REGISTER_LIBRARIAN")
    public void registerLibrarian(
            Librarian librarian
    ) {

        if (
                librarianCount
                >= librarians.length
        ) {

            System.out.println(
                    "Librarian storage is full."
            );

            return;
        }

        librarians[librarianCount] =
                librarian;

        librarianCount++;

        System.out.println(
                "Librarian registered successfully."
        );
    }


    // ==========================================
    // SEARCH
    // ==========================================

    @Override
    public Book searchById(int bookId) {

        for (int i = 0; i < bookCount; i++) {

            if (
                    books[i].getBookId()
                    == bookId
            ) {

                return books[i];
            }
        }

        throw new BookNotFoundException(
                "Book with ID "
                + bookId
                + " was not found."
        );
    }


    @Override
    public Book searchByTitle(
            String title
    ) {

        for (int i = 0; i < bookCount; i++) {

            if (
                    books[i]
                            .getTitle()
                            .equalsIgnoreCase(title)
            ) {

                return books[i];
            }
        }

        throw new BookNotFoundException(
                "Book with title \""
                + title
                + "\" was not found."
        );
    }


    public Member findMember(
            int memberId
    ) {

        for (
                int i = 0;
                i < memberCount;
                i++
        ) {

            if (
                    members[i].getMemberId()
                    == memberId
            ) {

                return members[i];
            }
        }

        throw new MemberNotFoundException(
                "Member with ID "
                + memberId
                + " was not found."
        );
    }


    // ==========================================
    // BORROW
    // ==========================================

    @Override
    @Auditable(action = "BORROW_BOOK")
    public void borrowBook(
            int bookId,
            int memberId
    )
            throws BookNotAvailableException,
            MemberLimitExceededException {

        Book book =
                searchById(bookId);

        Member member =
                findMember(memberId);

        if (!book.isAvailable()) {

            throw new BookNotAvailableException(
                    "Book is currently not available."
            );
        }

        if (!member.canBorrowBook()) {

            throw new MemberLimitExceededException(
                    "Member has reached the borrowing limit."
            );
        }

        if (
                transactionCount
                >= transactions.length
        ) {

            throw new IllegalStateException(
                    "Transaction storage is full."
            );
        }

        book.setAvailable(false);

        member.incrementBorrowedBookCount();

        Transaction transaction =
                new Transaction(
                        nextTransactionId,
                        bookId,
                        memberId
                );

        transactions[transactionCount] =
                transaction;

        transactionCount++;

        nextTransactionId++;

        System.out.println(
                "Book borrowed successfully."
        );

        System.out.println(
                "Transaction ID: "
                + transaction.getTransactionId()
        );

        System.out.println(
                "Borrow Date: "
                + transaction.getBorrowDate()
        );

        System.out.println(
                "Due Date: "
                + transaction.getDueDate()
        );
    }


    // ==========================================
    // RETURN
    // ==========================================

    @Override
    @Auditable(action = "RETURN_BOOK")
    public void returnBook(
            int bookId,
            int memberId
    ) {

        Book book =
                searchById(bookId);

        Member member =
                findMember(memberId);

        Transaction transaction =
                findActiveTransaction(
                        bookId,
                        memberId
                );

        if (transaction == null) {

            System.out.println(
                    "No active transaction found "
                    + "for this book and member."
            );

            return;
        }

        transaction.completeReturn();

        book.setAvailable(true);

        member.decrementBorrowedBookCount();

        System.out.println(
                "Book returned successfully."
        );

        System.out.println(
                "Return Date: "
                + transaction.getReturnDate()
        );

        System.out.printf(
                "Fine: $%.2f%n",
                transaction.getFine()
        );
    }


    private Transaction findActiveTransaction(
            int bookId,
            int memberId
    ) {

        for (
                int i = 0;
                i < transactionCount;
                i++
        ) {

            Transaction transaction =
                    transactions[i];

            if (
                    transaction.getBookId()
                            == bookId
                    &&
                    transaction.getMemberId()
                            == memberId
                    &&
                    !transaction.isReturned()
            ) {

                return transaction;
            }
        }

        return null;
    }


    // ==========================================
    // ADVANCED SEARCH
    // ==========================================

    public void searchBooks(
            Predicate<Book> condition
    ) {

        boolean found = false;

        System.out.println(
                "\n===== SEARCH RESULTS ====="
        );

        for (
                int i = 0;
                i < bookCount;
                i++
        ) {

            if (
                    condition.test(
                            books[i]
                    )
            ) {

                books[i]
                        .displayBookDetails();

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No matching books found."
            );
        }
    }


    public void searchAvailableBooks() {

        Predicate<Book> condition =
                book -> book.isAvailable();

        searchBooks(condition);
    }


    public void searchBooksByAuthor(
            String author
    ) {

        Predicate<Book> condition =
                book ->
                        book.getAuthor()
                                .equalsIgnoreCase(
                                        author
                                );

        searchBooks(condition);
    }


    public void searchBooksByTitleKeyword(
            String keyword
    ) {

        Predicate<Book> condition =
                book ->
                        book.getTitle()
                                .toLowerCase()
                                .contains(
                                        keyword
                                                .toLowerCase()
                                );

        searchBooks(condition);
    }


    // ==========================================
    // SORTING
    // ==========================================

    public void sortBooksById() {

        Book[] sorted =
                copyBooks();

        Arrays.sort(
                sorted,
                (book1, book2) ->
                        Integer.compare(
                                book1.getBookId(),
                                book2.getBookId()
                        )
        );

        displaySortedBooks(
                sorted,
                "BOOKS SORTED BY ID"
        );
    }


    public void sortBooksByTitle() {

        Book[] sorted =
                copyBooks();

        Arrays.sort(
                sorted,
                Comparator.comparing(
                        Book::getTitle,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        displaySortedBooks(
                sorted,
                "BOOKS SORTED BY TITLE"
        );
    }


    public void sortBooksByAuthor() {

        Book[] sorted =
                copyBooks();

        Arrays.sort(
                sorted,
                Comparator.comparing(
                        Book::getAuthor,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        displaySortedBooks(
                sorted,
                "BOOKS SORTED BY AUTHOR"
        );
    }


    private Book[] copyBooks() {

        return Arrays.copyOf(
                books,
                bookCount
        );
    }


    private void displaySortedBooks(
            Book[] sorted,
            String heading
    ) {

        if (sorted.length == 0) {

            System.out.println(
                    "No books available."
            );

            return;
        }

        System.out.println(
                "\n===== "
                + heading
                + " ====="
        );

        for (Book book : sorted) {

            book.displayBookDetails();
        }
    }


    // ==========================================
    // DISPLAY
    // ==========================================

    public void displayAllBooks() {

        if (bookCount == 0) {

            System.out.println(
                    "No books available."
            );

            return;
        }

        for (
                int i = 0;
                i < bookCount;
                i++
        ) {

            books[i]
                    .displayBookDetails();
        }
    }


    public void displayAllMembers() {

        if (memberCount == 0) {

            System.out.println(
                    "No members registered."
            );

            return;
        }

        for (
                int i = 0;
                i < memberCount;
                i++
        ) {

            members[i]
                    .displayDetails();
        }
    }


    public void displayAllLibrarians() {

        if (librarianCount == 0) {

            System.out.println(
                    "No librarians registered."
            );

            return;
        }

        for (
                int i = 0;
                i < librarianCount;
                i++
        ) {

            librarians[i]
                    .displayDetails();
        }
    }


    public void displayAllTransactions() {

        if (transactionCount == 0) {

            System.out.println(
                    "No transactions available."
            );

            return;
        }

        for (
                int i = 0;
                i < transactionCount;
                i++
        ) {

            transactions[i]
                    .displayTransactionDetails();
        }
    }
}