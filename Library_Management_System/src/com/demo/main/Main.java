package com.demo.main;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.demo.main.Model.Book;
import com.demo.main.Services.LibraryService;
import com.demo.main.controller.Controller;
import com.sun.net.httpserver.HttpServer;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Library Management System");
	     LibraryService libraryService = new LibraryService(1.0); // Fine rate per day
	     
	     //sample data
	     libraryService.addBook("978-3-16-148410-0", "Java Programming", "John Doe", "Programming");
	        libraryService.addBook("978-0-07-180855-2", "Effective Java", "Joshua Bloch", "Programming");
	        libraryService.addBook("978-1-4842-5434-8", "Pro Spring Boot", "Felipe Gutierrez", "Programming");
	        libraryService.addBook("978-1-4842-5425-7", "Spring Boot", "Felipe Gutierrez", "Programming");

	     
	     HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
	        
	     //context path set "/api"
	        server.createContext("/api", new Controller(libraryService, null));

	        server.setExecutor(null); 
	        server.start();
	        
	        System.out.println("Server started on port 8080");
	        
	        
		Scanner sc = new Scanner(System.in);
			
		while(true) {
			System.out.println("Press 1 - Add books to the library");
			System.out.println("Press 2 - Search book");
			System.out.println("Press 3 - Check out a book");
			System.out.println("Press 4 - Return a book and Calculate overdue fine");
			System.out.println("Press 5 - List all books");
			System.out.println("Press 6 - Library Catalog");
			System.out.println("Press 7- Order a book in online");
			
			String n = sc.next();
			switch (n) {
			case "1":
				System.out.println("case 1");
				addBook(libraryService);
				break;
			case "2":
				System.out.println("case 2");
				searchBook(libraryService);
				break;
			case "3":
				System.out.println("case 3");
				checkOut(libraryService);
				break;
			case "4":
				returnBook(libraryService);
				System.out.println("case 4");
				break;
			case "5":
				listAllBooks(libraryService);
				System.out.println("case 5");
				break;
			case "6":
				libraryCatalog(libraryService);
				System.out.println("case 5");
				break;
			case "7":
				searchBookOnline();
				System.out.println("case 5");
				break;

			default:
				System.out.println("Thank You");
				return;
			}
		}
		
		


	}
	
	static void addBook(LibraryService libraryService) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the ISBN Id of book");
		String isbnId = sc.nextLine();
		System.out.println("Enter the book name");
		String bookName = sc.nextLine();
		System.out.println("Enter the Author of book");
		String author = sc.nextLine();
		System.out.println("Enter the Gener of book");
		String gener = sc.nextLine();
       Book book =  libraryService.addBook(isbnId, bookName, author, gener);
       System.out.println(book.toString());		
	}
	
	static void listAllBooks(LibraryService libraryService) {
		List<Book> books = libraryService.listBooks();
		for(Book book : books){
			System.out.println(book.toString());
		}
		System.out.println("All books in the library");
	}
	
	static void checkOut(LibraryService libraryService) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the ISBN Id of book");
		String isbnId = sc.nextLine();
		System.out.println("Enter the number of days");
		long days = sc.nextLong();
		libraryService.checkOutBook(isbnId, LocalDate.now().plusDays(days));
		System.out.println("Book succesfully check out");
	}
	
	static void returnBook(LibraryService libraryService) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the ISBN Id");
		String isbnId = sc.nextLine();
		libraryService.returnBook(isbnId);
		double fineAmount = libraryService.calculateFine(isbnId);
		System.out.println("Book Returned sucessfully and Overdue fine : $"+fineAmount);
	}
	
	static void libraryCatalog(LibraryService libraryService) {
libraryService.displayLibraryCatalog(libraryService);
	}
	
	static void searchBook(LibraryService libraryService) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Press 1 - Search a book by title");
			System.out.println("Press 2 - Search a book by author");
			String choise = sc.nextLine();
			switch (choise) {
			case "1":
				System.out.println("Enter the book title");
				String title = sc.nextLine();
				Book book = libraryService.findBookByTitle(title);
				System.out.println(book.toString());
				break;
			case "2":
				System.out.println("Enter the author name");
				String author = sc.nextLine();
				List<Book> books = libraryService.findBooksByAuthor(author);
				for(Book singleBook : books) {
					System.out.println(singleBook.toString());
				}
				break;
			default:
				return;
			}
		}
		
	}
	
	static void searchBookOnline() throws IOException {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Press 1 - Search a book in other distributor/wholesaler");
			System.out.println("Press 2 - order a book in other distributor/wholesaler");
			String choise = sc.nextLine();
			switch (choise) {
			case "1":
				System.out.println("Enter the book ISBN Id ");
				String isbnid = sc.nextLine();
				
				 URL url = new URL("http://localhost:8080/api/books/externalApi?isbnid="+isbnid);
			        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			        conn.setRequestMethod("GET");
			        InputStream inputStream = conn.getInputStream();
			        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			        StringBuilder response = new StringBuilder();
			        char[] buffer = new char[1024];
			        int read;
			        while ((read = inputStreamReader.read(buffer)) != -1) {
			            response.append(buffer, 0, read);
			        }
			        inputStreamReader.close();
				System.out.println(response.toString());
				break;
			case "2":
				System.out.println("Enter the book ISBN Id ");
				String orderisbnid = sc.nextLine();
				
				 URL urll = new URL("http://localhost:8080/api/books/externalApi?order="+orderisbnid);
			        HttpURLConnection conn1 = (HttpURLConnection) urll.openConnection();
			        conn1.setRequestMethod("GET");
			        InputStream inputStream1 = conn1.getInputStream();
			        InputStreamReader inputStreamReaderr = new InputStreamReader(inputStream1);
			        StringBuilder responsee = new StringBuilder();
			        char[] bufferr = new char[1024];
			        int readd;
			        while ((readd = inputStreamReaderr.read(bufferr)) != -1) {
			        	responsee.append(bufferr, 0, readd);
			        }
			        inputStreamReaderr.close();
				System.out.println(responsee.toString());
				break;
			default:
				return;
			}
		}
		
	}

}
