package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Response;
import com.example.demo.model.CartBooksData;
import com.example.demo.service.IcartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	IcartService icartService;

	@PostMapping("/AddToCart")
	public ResponseEntity<Response> addToCart(@RequestHeader String token, @RequestParam int bookId) {
		CartBooksData cart = icartService.addToCart(token, bookId);
		Response response = new Response("Books Added Into Cart Successful", cart);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("UpdateBookCart")
	public ResponseEntity<Response> updateBookCart(@RequestHeader String token, @RequestParam int bookId,
			@RequestParam int qty) {
		CartBooksData cart = icartService.updateBooksOfCart(token, bookId, qty);
		Response response = new Response("Book Quantity Update Into Cart Successful", cart);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/Remove_Book_From_Cart")
	public ResponseEntity<Response> removeBookFromCart(@RequestHeader String token, @RequestParam int cartBookId) {
		icartService.removeBookFromCart(token, cartBookId);
		Response response = new Response("Removed Book for id: " + cartBookId, "Book Remove Successful");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/Show_Cart_Record")
	public ResponseEntity<Response> showUserCartRecords(@RequestHeader String token) {
		List<CartBooksData> cartRecord = icartService.showCartRecord(token);
		Response response = new Response("Cart record retrieved successfully for User", cartRecord);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
