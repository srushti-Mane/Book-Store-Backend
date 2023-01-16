package com.example.demo.service;

import java.util.List;

import com.example.demo.model.CartBooksData;

public interface IcartService {

	CartBooksData addToCart(String token, int bookId); 

    CartBooksData updateBooksOfCart(String token, int bookId, int qty); 

    String removeBookFromCart(String token, int cartBookId); 

    List<CartBooksData> showCartRecord(String token);

}
