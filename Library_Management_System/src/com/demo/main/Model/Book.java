package com.demo.main.Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Book {
    private String isbnId;
    private String bookName;
    private String author;
    private String genre;
    private boolean isAvailable;
    private LocalDate dueDate;

    public Book(String isbnId, String bookName, String author, String genre) {
        this.isbnId = isbnId;
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
    }


    public String getIsbnId() {
		return isbnId;
	}


	public void setIsbnId(String isbnId) {
		this.isbnId = isbnId;
	}


	public String getBookName() {
		return bookName;
	}


	public void setBookName(String bookName) {
		this.bookName = bookName;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public boolean isAvailable() {
		return isAvailable;
	}


	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}


	public LocalDate getDueDate() {
		return dueDate;
	}


	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}


	public void checkOut(LocalDate dueDate) {
        if (isAvailable) {
            isAvailable = false;
            this.dueDate = dueDate;
        }
    }

    public void returnBook() {
        isAvailable = true;
        dueDate = null;
    }

    public long calculateFine(double fineRatePerDay) {
        if (dueDate != null && LocalDate.now().isAfter(dueDate)) {
            long overdueDays = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
            return (long)(overdueDays * fineRatePerDay);
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return "ISBN: " + isbnId +
               ", Title: " + bookName +
               ", Author: " + author +
               ", Genre: " + genre +
               ", Available: " + (isAvailable() ? "Yes" : "No") +
               ", Due Date: " + (dueDate != null ? dueDate : "N/A");
    }

}

