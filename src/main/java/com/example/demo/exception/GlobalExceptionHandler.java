package com.example.demo.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(value = BookStoreException.class)
	    public String handleBookStoreException(BookStoreException bookStoreException) {
	        return bookStoreException.getMessage();
	    }

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public List<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
	        return exception.getBindingResult()
	                .getAllErrors().stream()
	                .map(ObjectError::getDefaultMessage)
	                .collect(Collectors.toList());
	    }
}
