package com.demo.main.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.main.Model.Book;
import com.demo.main.Services.BookServices;
import com.demo.main.Services.LibraryService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Controller implements HttpHandler {
	private LibraryService libraryService;
	private BookServices bookService;

	public Controller(LibraryService libraryService, BookServices bookService) {
		this.libraryService = libraryService;
		this.bookService = bookService;
	}

	public List<Book> getAllBooks() {
		return libraryService.listBooks();
	}
	
	public Book searchBookByIsbnId(String isbnId) {
		return libraryService.findBookByIsbnId(isbnId);
	}
	
	public Book searchBookByTitle(String title) {
		return libraryService.findBookByTitle(title);
	}

	public List<Book> searchBooksByAuthor(String author) {
		return libraryService.findBooksByAuthor(author);
	}
	
	public List<Book> searchBooksByGener(String gener) {
		return libraryService.findBooksByGener(gener);
	}

	public boolean avaliablityCheck(String isbnId) {
		return libraryService.avaliablityCheck(isbnId);
		
	}
//	public void checkoutBook(String isbnId, LocalDate dueDate) {
//		libraryService.checkOutBook(isbnId, dueDate);
//	}
//
//	public void returnBook(String isbnId) {
//		libraryService.returnBook(isbnId);
//	}
//
//	public long calculateFine(String isbnId) {
//		return libraryService.calculateFine(isbnId);
//	}
//
//	public void orderBookFromDistributor(String isbn) {
//		bookService.orderBook(isbn);
//	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String path = exchange.getRequestURI().getPath();
		String[] pathParts = path.split("/");
		System.out.println("pathParts[2] " + pathParts[3]);
		
		if ("GET".equals(exchange.getRequestMethod())) {
			String query = exchange.getRequestURI().getQuery();
            Map<String, String> queryParams = parseQueryParams(query);
            System.out.println("queryParams :"+queryParams);
                   
			if("search".equals(pathParts[3]) && queryParams.get("title") != null) {
				System.out.println("title called");

				Book book = searchBookByTitle(queryParams.get("title").toString());
				String response = book.toString();

				exchange.sendResponseHeaders(200, response.getBytes().length);
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}else if ("bookDetails".equals(pathParts[3])) {
				Book book = searchBookByIsbnId(pathParts[4]);
				String response = book.toString();

				exchange.sendResponseHeaders(200, response.getBytes().length);
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}else if ("availability".equals(pathParts[3])) {
				Boolean check = avaliablityCheck(pathParts[4]);
				
				String response = check ? "yes, It's avaliable" : "No, It's not avaliable";

				exchange.sendResponseHeaders(200, response.getBytes().length);
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
				
			}else if ("search".equals(pathParts[3]) && queryParams.get("author") != null) {
				System.out.println("author called");
				List<Book> books = searchBooksByAuthor(queryParams.get("author").toString());
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < books.size(); i++) {
					sb.append(books.get(i));
					if (i < books.size() - 1) {
						sb.append(", "); // Delimiter between elements
					}
				}

				String response = sb.toString();

				exchange.sendResponseHeaders(200, response.getBytes().length);
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}else if ("search".equals(pathParts[3]) && queryParams.get("gener") != null) {
				List<Book> books = searchBooksByGener(queryParams.get("gener").toString());
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < books.size(); i++) {
					sb.append(books.get(i));
					if (i < books.size() - 1) {
						sb.append(", \n"); // Delimiter between elements
					}
				}

				String response = sb.toString();

				exchange.sendResponseHeaders(200, response.getBytes().length);
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}else if ("libraryCatalog".equals(pathParts[3])) {
				List<Book> books = getAllBooks();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < books.size(); i++) {
					sb.append(books.get(i));
					if (i < books.size() - 1) {
						sb.append(", \n"); // Delimiter between elements
					}
				}

				String response = sb.toString();

				exchange.sendResponseHeaders(200, response.getBytes().length);
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}else {
				String response = "Invalid Request";
				exchange.sendResponseHeaders(403, response.getBytes().length);
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}
			
		}else {
			String response = "Method Not Found";
			exchange.sendResponseHeaders(404, response.getBytes().length);
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
		
		
		
	}
		
		
		
//		
//		
//		if ("GET".equals(exchange.getRequestMethod()) && "searchBookByTitle".equals(pathParts[2])) {
//			Book book = searchBookByTitle(pathParts[3]);
//			String response = book.toString();
//
//			exchange.sendResponseHeaders(200, response.getBytes().length);
//			OutputStream os = exchange.getResponseBody();
//			os.write(response.getBytes());
//			os.close();
//		} else if ("GET".equals(exchange.getRequestMethod()) && "searchBookByAuthor".equals(pathParts[2])) {
//			List<Book> books = searchBooksByAuthor(pathParts[3]);
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < books.size(); i++) {
//				sb.append(books.get(i));
//				if (i < books.size() - 1) {
//					sb.append(", \n"); // Delimiter between elements
//				}
//			}
//
//			String response = sb.toString();
//
//			exchange.sendResponseHeaders(200, response.getBytes().length);
//			OutputStream os = exchange.getResponseBody();
//			os.write(response.getBytes());
//			os.close();
//		} else if ("GET".equals(exchange.getRequestMethod()) && "libraryCatalog".equals(pathParts[2])) {
//			List<Book> books = getAllBooks();
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < books.size(); i++) {
//				sb.append(books.get(i));
//				if (i < books.size() - 1) {
//					sb.append(", "); // Delimiter between elements
//				}
//			}
//
//			String response = sb.toString();
//			exchange.sendResponseHeaders(200, response.getBytes().length);
//			OutputStream os = exchange.getResponseBody();
//			os.write(response.getBytes());
//			os.close();
//		}
//
//		else {
//			// Handle other request methods
//			String response = "Invalid Request";
//			exchange.sendResponseHeaders(403, response.getBytes().length);
//			OutputStream os = exchange.getResponseBody();
//			os.write(response.getBytes());
//			os.close();
//		}
//	}
	
	private Map<String, String> parseQueryParams(String query) {
        Map<String, String> queryParams = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length > 0) {
                    String key = keyValue[0];
                    String value = keyValue.length > 1 ? keyValue[1] : "";
                    queryParams.put(key, value);
                }
            }
        }
        return queryParams;
    }
}
