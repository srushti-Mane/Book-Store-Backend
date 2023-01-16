package com.example.demo.exception;

public class BookStoreException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;

	public BookStoreException(String message) {
		super();
		this.message = message;
	}

	 public BookStoreException(){

	    }
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    

}
