package com.library.model;

public class Book {

    private final int bookId;
    private String title;
    private String author;
    private boolean available;

    public Book(
            int bookId,
            String title,
            String author
    ) {

        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void displayBookDetails() {

        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);

       if (available) {
        System.out.println("Status: Available");
    } else {
        System.out.println("Status: Borrowed");
    }
    }
}