package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CartBooksData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartBookId;
	private int quantity;
	private double totalPrice;

	@ManyToOne()
	@JoinColumn(name = "cartId")
	private CartModel cart;

	@ManyToOne()
	@JoinColumn(name = "bookId")
	private BookModel books;

	public CartBooksData(double totalPrice, CartModel cart, BookModel books) {
		this.totalPrice = totalPrice;
		this.cart = cart;
		this.books = books;
	}
}
