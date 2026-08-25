package com.library;

import java.util.Scanner;

import com.library.annotation.AuditProcessor;
import com.library.exception.BookNotAvailableException;
import com.library.exception.BookNotFoundException;
import com.library.exception.MemberLimitExceededException;
import com.library.exception.MemberNotFoundException;
import com.library.model.Book;
import com.library.model.Librarian;
import com.library.model.Member;
import com.library.model.Person;
import com.library.service.LibraryService;

public class LibraryApplication {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            LibraryService library =
                    new LibraryService(
                            100,
                            50
                    );

            boolean running = true;

            System.out.println(
                    "===================================="
            );

            System.out.println(
                    "     LIBRARY MANAGEMENT SYSTEM"
            );

            System.out.println(
                    "===================================="
            );


            while (running) {

                displayMenu();

                int choice =
                        readInt(
                                scanner,
                                "Enter your Choice: "
                        );


                switch (choice) {

                    case 1:
                        addBook(
                                scanner,
                                library
                        );
                        break;

                    case 2:
                        registerMember(
                                scanner,
                                library
                        );
                        break;

                    case 3:
                        registerLibrarian(
                                scanner,
                                library
                        );
                        break;

                    case 4:
                        library.displayAllBooks();
                        break;

                    case 5:
                        searchBook(
                                scanner,
                                library
                        );
                        break;

                    case 6:
                        borrowBook(
                                scanner,
                                library
                        );
                        break;

                    case 7:
                        returnBook(
                                scanner,
                                library
                        );
                        break;

                    case 8:
                        viewMember(
                                scanner,
                                library
                        );
                        break;

                    case 9:
                        library.displayAllMembers();
                        break;

                    case 10:
                        library.displayAllLibrarians();
                        break;

                    case 11:
                        library.displayAllTransactions();
                        break;

                    case 12:
                        advancedSearch(
                                scanner,
                                library
                        );
                        break;

                    case 13:
                        sortBooks(
                                scanner,
                                library
                        );
                        break;

                    case 14:

                        System.out.println(
                                "Thank you for using "
                                + "the Library Management System."
                        );

                        running = false;

                        break;

                    default:

                        System.out.println(
                                "Invalid option. Choose 1 to 14."
                        );
                }
            }
        }
    }


    private static void displayMenu() {

        System.out.println(
                "\n========== Library Management =========="
        );

        System.out.println("1. Add Book");
        System.out.println("2. Register Member");
        System.out.println("3. Register Librarian");
        System.out.println("4. View All Books");
        System.out.println("5. Search Book");
        System.out.println("6. Borrow Book");
        System.out.println("7. Return Book");
        System.out.println("8. View Member");
        System.out.println("9. View All Members");
        System.out.println("10. View All Librarians");
        System.out.println("11. View Transactions");
        System.out.println("12. Advanced Book Search");
        System.out.println("13. Sort Books");
        System.out.println("14. Exit");
    }


    private static void addBook(
            Scanner scanner,
            LibraryService library
    ) {

        int bookId =
                readInt(
                        scanner,
                        "Enter Book ID: "
                );

        System.out.print(
                "Enter Book Title: "
        );

        String title =
                scanner.nextLine();

        System.out.print(
                "Enter Author: "
        );

        String author =
                scanner.nextLine();

        Book book =
                new Book(
                        bookId,
                        title,
                        author
                );

        AuditProcessor.auditMethod(
                LibraryService.class,
                "addBook"
        );

        library.addBook(book);
    }


    private static void registerMember(
            Scanner scanner,
            LibraryService library
    ) {

        int memberId =
                readInt(
                        scanner,
                        "Enter Member ID: "
                );

        System.out.print(
                "Enter Member Name: "
        );

        String name =
                scanner.nextLine();

        Member member =
                new Member(
                        memberId,
                        name
                );

        AuditProcessor.auditMethod(
                LibraryService.class,
                "registerMember"
        );

        library.registerMember(member);


        // Polymorphism demonstration
        Person person = member;

        System.out.println(
                "\nPolymorphic Person reference:"
        );

        person.displayDetails();
    }


    private static void registerLibrarian(
            Scanner scanner,
            LibraryService library
    ) {

        int librarianId =
                readInt(
                        scanner,
                        "Enter Librarian ID: "
                );

        System.out.print(
                "Enter Librarian Name: "
        );

        String name =
                scanner.nextLine();

        Librarian librarian =
                new Librarian(
                        librarianId,
                        name
                );

        AuditProcessor.auditMethod(
                LibraryService.class,
                "registerLibrarian"
        );

        library.registerLibrarian(
                librarian
        );


        // Same parent reference,
        // different subclass
        Person person = librarian;

        person.displayDetails();
    }


    private static void searchBook(
            Scanner scanner,
            LibraryService library
    ) {

        try {

            int bookId =
                    readInt(
                            scanner,
                            "Enter Book ID: "
                    );

            Book book =
                    library.searchById(
                            bookId
                    );

            book.displayBookDetails();

        } catch (
                BookNotFoundException e
        ) {

            System.out.println(
                    "Error: "
                    + e.getMessage()
            );
        }
    }


    private static void borrowBook(
            Scanner scanner,
            LibraryService library
    ) {

        try {

            int bookId =
                    readInt(
                            scanner,
                            "Enter Book ID: "
                    );

            int memberId =
                    readInt(
                            scanner,
                            "Enter Member ID: "
                    );

            AuditProcessor.auditMethod(
                    LibraryService.class,
                    "borrowBook"
            );

            library.borrowBook(
                    bookId,
                    memberId
            );

        } catch (
                BookNotFoundException
                | MemberNotFoundException
                | BookNotAvailableException
                | MemberLimitExceededException e
        ) {

            System.out.println(
                    "Error: "
                    + e.getMessage()
            );
        }
    }


    private static void returnBook(
            Scanner scanner,
            LibraryService library
    ) {

        try {

            int bookId =
                    readInt(
                            scanner,
                            "Enter Book ID: "
                    );

            int memberId =
                    readInt(
                            scanner,
                            "Enter Member ID: "
                    );

            AuditProcessor.auditMethod(
                    LibraryService.class,
                    "returnBook"
            );

            library.returnBook(
                    bookId,
                    memberId
            );

        } catch (
                BookNotFoundException
                | MemberNotFoundException e
        ) {

            System.out.println(
                    "Error: "
                    + e.getMessage()
            );
        }
    }


    private static void viewMember(
            Scanner scanner,
            LibraryService library
    ) {

        try {

            int memberId =
                    readInt(
                            scanner,
                            "Enter Member ID: "
                    );

            Person person =
                    library.findMember(
                            memberId
                    );

            person.displayDetails();

        } catch (
                MemberNotFoundException e
        ) {

            System.out.println(
                    "Error: "
                    + e.getMessage()
            );
        }
    }


    private static void advancedSearch(
            Scanner scanner,
            LibraryService library
    ) {

        System.out.println(
                "\n===== ADVANCED SEARCH ====="
        );

        System.out.println(
                "1. Available Books"
        );

        System.out.println(
                "2. Search by Author"
        );

        System.out.println(
                "3. Search by Title Keyword"
        );


        int choice =
                readInt(
                        scanner,
                        "Choose option: "
                );


        switch (choice) {

            case 1:

                library.searchAvailableBooks();

                break;


            case 2:

                System.out.print(
                        "Enter Author: "
                );

                String author =
                        scanner.nextLine();

                library.searchBooksByAuthor(
                        author
                );

                break;


            case 3:

                System.out.print(
                        "Enter Title Keyword: "
                );

                String keyword =
                        scanner.nextLine();

                library.searchBooksByTitleKeyword(
                        keyword
                );

                break;


            default:

                System.out.println(
                        "Invalid search option."
                );
        }
    }


    private static void sortBooks(
            Scanner scanner,
            LibraryService library
    ) {

        System.out.println(
                "\n===== SORT BOOKS ====="
        );

        System.out.println("1. Book ID");
        System.out.println("2. Title");
        System.out.println("3. Author");


        int choice =
                readInt(
                        scanner,
                        "Choose option: "
                );


        switch (choice) {

            case 1:
                library.sortBooksById();
                break;

            case 2:
                library.sortBooksByTitle();
                break;

            case 3:
                library.sortBooksByAuthor();
                break;

            default:

                System.out.println(
                        "Invalid sorting option."
                );
        }
    }


    private static int readInt(
            Scanner scanner,
            String prompt
    ) {

        while (true) {

            System.out.print(prompt);

            String input =
                    scanner.nextLine();

            try {

                return Integer.parseInt(
                        input.trim()
                );

            } catch (
                    NumberFormatException e
            ) {

                System.out.println(
                        "Please enter a valid whole number."
                );
            }
        }
    }
}