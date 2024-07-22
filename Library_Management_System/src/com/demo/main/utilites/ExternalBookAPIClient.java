package com.demo.main.utilites;

import java.util.ArrayList;
import java.util.List;

import com.demo.main.Model.Book;
import com.demo.main.Model.Library;

public class ExternalBookAPIClient {
	Library library = new Library();

    public Book searchBookByIsbn(String isbn) {
    	library.addBook(new Book("978-3-16-148410-0", "Java Programming", "John Doe", "Programming"));
    	library.addBook(new Book("978-0-07-180855-2", "Effective Java", "Joshua Bloch", "Programming"));
    	library.addBook(new Book("978-1-4842-5434-8", "Pro Spring Boot", "Felipe Gutierrez", "Programming"));
    	library.addBook(new Book("978-1-4842-5425-7", "Spring Boot", "Felipe Gutierrez", "Programming"));
    	
    	return library.searchByisbnId(isbn);
    }

    public String placeOrder(String isbn) {
//    	Book book = library.searchByisbnId(isbn);
//    	if (book.isAvailable() != false ) {
    		return "Order placed for ISBN: " + isbn;
//    	}
//    	return "Order not placed book not avaliable";
    }
}

