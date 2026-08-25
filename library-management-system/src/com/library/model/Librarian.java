package com.library.model;

public class Librarian extends Person {

    public Librarian(
            int librarianId,
            String name
    ) {

        super(librarianId, name);
    }

    public int getLibrarianId() {
        return getId();
    }

    @Override
    public void displayDetails() {

        System.out.println("----------------------------");

        System.out.println(
                "Librarian ID: "
                + getLibrarianId()
        );

        System.out.println(
                "Librarian Name: "
                + getName()
        );

        System.out.println("----------------------------");
    }
}