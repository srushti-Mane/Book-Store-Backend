package com.example.demo.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.LoginDTO;
import com.example.demo.exception.BookStoreException;
import com.example.demo.model.BookModel;
import com.example.demo.model.UserModel;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utility.JwtUtils;
@Service
public class BookService implements IbookService {

	    @Autowired
	    BookRepository bookRepository;

	    @Autowired
	    UserRepository userRepository;

	    @Autowired
	    ModelMapper modelMapper;

	    @Autowired
	    JwtUtils jwtUtils;
	
	
	@Override
	public BookModel addBooks(String token, BookDTO bookDTO) {
		 LoginDTO loginDTO = jwtUtils.decodeToken(token);
	        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
	        if (userRepository.findByEmail(user.getEmail()).getRole().equals("Admin") && userRepository.findByEmail(user.getEmail()).isLogin()) {
	            BookModel bookModel = modelMapper.map(bookDTO, BookModel.class);
	            return bookRepository.save(bookModel);
	        }
	        throw new BookStoreException("Only Admin can add Books"+"\nplease login Application As admin");
	    }


	@Override
	public BookModel updateBooksData(String token, int id, BookDTO bookDTO) {
		 LoginDTO loginDTO = jwtUtils.decodeToken(token);
	        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
	        if (userRepository.findByEmail(user.getEmail()).getRole().equals("Admin") && userRepository.findByEmail(user.getEmail()).isLogin()) {
	            if (bookRepository.findById(id).isPresent()) {
	                BookModel book = bookRepository.findById(id).get();
	                BookModel updateBook = modelMapper.map(bookDTO, BookModel.class);
	                updateBook.setBookId(id);
	                if (updateBook.getBookName() == null) {
	                    updateBook.setBookName(book.getBookName());
	                }
	                if (updateBook.getAuthorName() == null) {
	                    updateBook.setAuthorName(book.getAuthorName());
	                }
	                if (updateBook.getBookQuantity() == 0) {
	                    updateBook.setBookQuantity(book.getBookQuantity());
	                }
	                if (updateBook.getPrice() == 0) {
	                    updateBook.setPrice(book.getPrice());
	                }
	                return bookRepository.save(updateBook);
	            }
	            throw new BookStoreException("Invalid Id");
	        }
	        throw new BookStoreException("Only Admin can Update Books"+"\nplease login Application As admin");
	    }

	

	@Override
	public String deleteBookById(String token, int id) {
		LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).getRole().equals("Admin") && userRepository.findByEmail(user.getEmail()).isLogin()) {
            if (bookRepository.findById(id).isPresent()) {
                bookRepository.deleteById(id);
                return "Book Data Deleted successful";
            }
            throw new BookStoreException("Book Not Found "+"\nInvalid Id ");
        }
        throw new BookStoreException("Only Admin can Delete Books"+"\nplease login Application As admin");
	}

	@Override
	public List<BookModel> showAllBooks() {
		if (bookRepository.findAll().isEmpty()) {
            throw new BookStoreException("Books Not Available");
        }
        return bookRepository.findAll();
	}

	@Override
	public BookModel getBookByID(int id) {
		 if (bookRepository.findById(id).isPresent()) {
	            return bookRepository.findById(id).get();
	        }
	        throw new BookStoreException("Invalid id");
	    }


	@Override
	public List<BookModel> searchBookByName(String bookName) {
		 List<BookModel> bookList = bookRepository.findByBookName(bookName);
	        if (bookList.isEmpty()) {
	            throw new BookStoreException("Book with name " + bookName + " is not found!");
	        }
	        return bookList;
	}

	@Override
	public List<BookModel> searchBookByAuthorName(String authorName) {
		 List<BookModel> bookModelList = bookRepository.findAllByAuthorName(authorName);
	        if (bookModelList.isEmpty()) {
	            throw new BookStoreException("Book with Author Name " + authorName + " is not found!");
	        }
	        return bookModelList;
	}

	@Override
	public List<BookModel> sortBookByPriceHighToLow() {
		 return bookRepository.findAll(Sort.by(Sort.Direction.DESC,"price"));
	}

	@Override
	public List<BookModel> sortBookByPriceLowToHigh() {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC,"price"));

	}

	@Override
	public List<BookModel> sortBooksByNewestArrivals() {
        return bookRepository.findAll(Sort.by(Sort.Direction.DESC,"bookId"));

	}

}
