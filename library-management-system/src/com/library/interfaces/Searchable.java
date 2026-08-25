package com.library.interfaces;

import com.library.model.Book;

public interface Searchable {

    Book searchById(int bookId);

    Book searchByTitle(String title);
}