package com.demo.main.utilites;

import com.demo.main.Model.Book;

public class ExternalBookAPIClient {
    public Book searchBookByIsbn(String isbn) {
        // Simulate external API call
        // For the sake of example, returning a dummy book
        return new Book(isbn, "Sample Book", "Sample Author", "Fiction");
    }

    public void placeOrder(String isbn) {
        // Simulate ordering book via external API
        System.out.println("Order placed for ISBN: " + isbn);
    }
}

