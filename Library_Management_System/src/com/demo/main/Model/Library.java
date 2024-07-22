package com.demo.main.Model;
import java.util.ArrayList;
import java.util.List;


public class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book searchByTitle(String title) {
        for (Book book : books) {
            if (book.getBookName().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
    
    public List<Book> searchByAuthor(String author) {
        List<Book> authorBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                authorBooks.add(book);
            }
        }
        return authorBooks;
    }
    
    public List<Book> searchByGener(String gener) {
    	 List<Book> generBooks = new ArrayList<>();
         for (Book book : books) {
             if (book.getGenre().equalsIgnoreCase(gener)) {
            	 generBooks.add(book);
             }
         }
         return generBooks;
    }
    
    public Book searchByisbnId(String isbnId) {
        for (Book book : books) {
            if (book.getIsbnId().equalsIgnoreCase(isbnId)) {
                return book;
            }
        }
        return null;
    }
    
    public boolean avaliablityCheck(String isbnId) {
        for (Book book : books) {
            if (book.getIsbnId().equalsIgnoreCase(isbnId)) {
                return true;
            }
        }
        return false;
    }

}

