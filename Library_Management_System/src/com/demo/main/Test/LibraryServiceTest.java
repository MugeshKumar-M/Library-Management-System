package com.demo.main.Test;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import com.demo.main.Model.Book;
import com.demo.main.Services.LibraryService;

import junit.framework.TestCase;

public class LibraryServiceTest extends TestCase {
    private LibraryService libraryService;

    @BeforeEach
    public void setUp() {
        libraryService = new LibraryService(2.0); // Assume $2 per day fine
    }

    @Test
    public void testAddAndSearchBook() {
        libraryService.addBook("12345", "Effective Java", "Joshua Bloch", "Programming");
        Book book = libraryService.findBookByTitle("Effective Java");
        assertNotNull(book);
        assertEquals("Joshua Bloch", book.getAuthor());
    }

    @Test
    public void testCheckoutAndReturnBook() {
        libraryService.addBook("12345", "Effective Java", "Joshua Bloch", "Programming");
        libraryService.checkOutBook("12345", LocalDate.now().plusDays(7));
        Book book = libraryService.findBookByTitle("Effective Java");
        assertFalse(book.isAvailable());

        libraryService.returnBook("12345");
        assertTrue(book.isAvailable());
    }

//    @Test
//    public void testCalculateFine() {
//        libraryService.addBook("12345", "Effective Java", "Joshua Bloch", "Programming");
//        libraryService.checkOutBook("12345", LocalDate.now().minusDays(10)); // 10 days overdue
//        long fine = libraryService.calculateFine("12345");
//        assertEquals(20, fine); // 10 days * $2 fine rate
//    }
}
