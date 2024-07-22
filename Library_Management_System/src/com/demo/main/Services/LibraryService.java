package com.demo.main.Services;

import java.time.LocalDate;
import java.util.List;

import com.demo.main.Model.Book;
import com.demo.main.Model.Library;

public class LibraryService {
    private Library library;
    private double fineRatePerDay;

    public LibraryService(double fineRatePerDay) {
        library = new Library();
        this.fineRatePerDay = fineRatePerDay;
    }

    public Book addBook(String isbnId, String bookName, String author, String genre) {
        Book book = new Book(isbnId, bookName, author, genre);
        library.addBook(book);
        return book;
    }

    public List<Book> listBooks() {
        return library.getBooks();
    }
    
    public Book findBookByIsbnId(String isbnId) {
        return library.searchByisbnId(isbnId);
    }

    public Book findBookByTitle(String title) {
        return library.searchByTitle(title);
    }

    public List<Book> findBooksByAuthor(String author) {
        return library.searchByAuthor(author);
    }
    
    public List<Book> findBooksByGener(String gener) {
        return library.searchByGener(gener);
    }
    
    public boolean avaliablityCheck(String isbnId) {
		return library.avaliablityCheck(isbnId);
    }

    public void checkOutBook(String isbnId, LocalDate dueDate) {
        Book book = library.getBooks().stream().filter(b -> b.getIsbnId().equals(isbnId)).findFirst().orElse(null);
        if (book != null) {
            book.checkOut(dueDate);
        }
    }

    public void returnBook(String isbnId) {
        Book book = library.getBooks().stream().filter(b -> b.getIsbnId().equals(isbnId)).findFirst().orElse(null);
        if (book != null) {
            book.returnBook();
        }
    }

    public long calculateFine(String isbnId) {
        Book book = library.getBooks().stream().filter(b -> b.getIsbnId().equals(isbnId)).findFirst().orElse(null);
        if (book != null) {
            return book.calculateFine(fineRatePerDay);
        }
        return 0;
    }
    
    public  void displayLibraryCatalog(LibraryService libraryService) {
        List<Book> books = libraryService.listBooks();
        System.out.println("\nLibrary Catalog:");
        for (Book book : books) {
            System.out.println("ISBN: " + book.getIsbnId() +
                    ", Title: " + book.getBookName() +
                    ", Author: " + book.getAuthor() +
                    ", Genre: " + book.getGenre() +
                    ", Available: " + (book.isAvailable() ? "Yes" : "No") +
                    ", Due Date: " + (book.getDueDate() != null ? book.getDueDate() : "N/A"));    }
    }
}

