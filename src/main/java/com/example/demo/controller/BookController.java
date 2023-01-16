package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Response;
import com.example.demo.dto.BookDTO;
import com.example.demo.model.BookModel;
import com.example.demo.service.IbookService;
@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
    IbookService ibookService;

	@PostMapping("/Add_Books/Admin")
    public ResponseEntity<Response> addBooks(@RequestHeader String token, @RequestBody BookDTO bookDTO) {
        ibookService.addBooks(token,bookDTO);
        Response response = new Response( "Book Added Successful",bookDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	 @PutMapping("/Update_Books_Data/Admin")
	    public ResponseEntity<Response> updateBook(@RequestHeader String token,@RequestParam int id, @RequestBody BookDTO bookDTO) {
	        BookModel update = ibookService.updateBooksData(token,id,bookDTO);
	        Response response = new Response( "Book Update Successful",update);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    
	 
	    @DeleteMapping("/Delete_Book/Admin")
	    public ResponseEntity<Response> deleteBook(@RequestHeader String token, @RequestParam int id) {
	        ibookService.deleteBookById(token, id);
	        Response response = new Response("book deleted for id :" +id+" ", "Book delete Successful");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    @GetMapping("/Show All Books Data")
	    public ResponseEntity<Response> showAllBooksData(){
	        List<BookModel> bookModelList = ibookService.showAllBooks();
	        Response response = new Response( "All Books Data" ,bookModelList);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    
	    @GetMapping("/Find_Book_By_Id")
	    public ResponseEntity<Response> getBookById(@RequestParam int id) {
	        BookModel bookModel = ibookService.getBookByID(id);
	        Response response = new Response( "successfully record founded for given id: " + id,bookModel);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    
	    @GetMapping("/Search_Books_By_Name")
	    public ResponseEntity<Response> searchBooksByName(@RequestParam String bookName) {
	        List<BookModel> bookModelList = ibookService.searchBookByName(bookName);
	        Response response = new Response( "successfully record founded for given book name: " + bookName,bookModelList);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    
	    @GetMapping("/Search_Books_By_Author_Name")
	    public ResponseEntity<Response> searchBooksByAuthorName(@RequestParam String authorName) {
	        List<BookModel> bookModelList = ibookService.searchBookByAuthorName(authorName);
	        Response response = new Response("successfully record founded for given Author name: " + authorName,bookModelList);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    
	    @GetMapping("/Sort_Books_By_Price_HighToLow")
	    public ResponseEntity<Response> sortBooksByPriceHighToLow() {
	        List<BookModel> sortedList = ibookService.sortBookByPriceHighToLow();
	        Response response = new Response( "Sort Books By Price High To Low",sortedList);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    
	    @GetMapping("/Sort_Books_By_Price_LowToHigh")
	    public ResponseEntity<Response> sortBooksByPriceLowToHigh() {
	        List<BookModel> sortedList = ibookService.sortBookByPriceLowToHigh();
	        Response response = new Response("Sort Books By Price Low To High",sortedList);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    
	    @GetMapping("/Sort_Books_By_Newest_Arrivals")
	    public ResponseEntity<Response> sortBooksByNewestArrivals() {
	        List<BookModel> sortedList = ibookService.sortBooksByNewestArrivals();
	        Response response = new Response( "Sort Books By Newest Arrivals",sortedList);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
}
