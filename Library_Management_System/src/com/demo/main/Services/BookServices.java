package com.demo.main.Services;

import com.demo.main.Model.Book;
import com.demo.main.utilites.ExternalBookAPIClient;

public class BookServices {
    private ExternalBookAPIClient apiClient;

    public BookServices(ExternalBookAPIClient apiClient) {
        this.apiClient = apiClient;
    }

    public Book searchBook(String isbn) {
        return apiClient.searchBookByIsbn(isbn);
    }

    public String orderBook(String isbn) {
       return apiClient.placeOrder(isbn);
    }
}

