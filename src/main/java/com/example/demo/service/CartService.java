package com.example.demo.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.exception.BookStoreException;
import com.example.demo.model.BookModel;
import com.example.demo.model.CartBooksData;
import com.example.demo.model.CartModel;
import com.example.demo.model.UserModel;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CartBooksRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utility.JwtUtils;

@Service
public class CartService implements IcartService {

	@Autowired
	CartBooksRepository cartBooksRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	JwtUtils jwtUtils;

	@Override
	public CartBooksData addToCart(String token, int bookId) {
		LoginDTO loginDTO = jwtUtils.decodeToken(token);
		UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
		CartModel cartModel = cartRepository.findById(user.getCartModel().getCartId()).get();
		if (userRepository.findByEmail(user.getEmail()).isLogin()) {
			BookModel book = bookRepository.findById(bookId).get();
			if (book != null) {
				//if (cartBooksRepository.findByCartIdAndBookId(cartModel.getCartId(), bookId) == null) {
					CartBooksData cartBooks = new CartBooksData(book.getPrice(), cartModel, book);
					cartBooks.setQuantity(1);
					return cartBooksRepository.save(cartBooks);
				//}
				//throw new BookStoreException("This Book is Already Exist into Your Cart");
			}
			throw new BookStoreException("Book Not Found");
		}
		throw new BookStoreException("Please first Login Application");
	}

	@Override
	public CartBooksData updateBooksOfCart(String token, int bookId, int qty) {
		LoginDTO loginDTO = jwtUtils.decodeToken(token);
		UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
		CartModel cartModel = cartRepository.findById(user.getCartModel().getCartId()).get();
		if (userRepository.findByEmail(user.getEmail()).isLogin()) {
			BookModel book = bookRepository.findById(bookId).get();
			CartBooksData cartBooks = cartBooksRepository.findByCartIdAndBookId(cartModel.getCartId(), bookId);
			if (cartBooks != null) {
				if (qty <= book.getBookQuantity()) {
					double totalPrice = calculateTotalPrice(qty, book.getPrice());
					cartBooks.setQuantity(qty);
					cartBooks.setTotalPrice(totalPrice);
					return cartBooksRepository.save(cartBooks);
				}
				throw new BookStoreException(qty + " Books Not available in Stock" + "\nOnly " + book.getBookQuantity()
						+ " Books available in stock");
			}
			throw new BookStoreException("Book Not Found in Cart");
		}
		throw new BookStoreException("Please first Login Application");
	}

	private double calculateTotalPrice(int quantity, int bookPrice) {
		return quantity * bookPrice;
	}

	@Override
	public String removeBookFromCart(String token, int cartBookId) {
		LoginDTO loginDTO = jwtUtils.decodeToken(token);
		UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
		CartModel cartModel = cartRepository.findById(user.getCartModel().getCartId()).get();
		if (userRepository.findByEmail(user.getEmail()).isLogin()) {
			if (cartBooksRepository.findById(cartBookId).isPresent()) {
				if (cartBooksRepository.findById(cartBookId).get().getCart().getCartId() == cartModel.getCartId()) {
					cartBooksRepository.deleteById(cartBookId);
					return "Delete Successful";
				}
				throw new BookStoreException("Invalid Cart Book ID");
			}
			throw new BookStoreException("Book Not Found");
		}
		throw new BookStoreException("Please first Login Application");
	}

	@Override
	public List<CartBooksData> showCartRecord(String token) {
		LoginDTO loginDTO = jwtUtils.decodeToken(token);
		UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
		if (userRepository.findByEmail(user.getEmail()).isLogin()) {
			List<CartBooksData> availableBooksInCart = cartBooksRepository
					.findByCart_CartId(user.getCartModel().getCartId());
			if (availableBooksInCart.isEmpty()) {
				throw new BookStoreException("Empty Cart");
			}
			return availableBooksInCart;
		}
		throw new BookStoreException("Please first Login Application");
	}
}
