package com.example.demo.dto;

import lombok.Data;

@Data
public class BookDTO {

	private String bookName;
    private String authorName;
    private int bookQuantity;
    private int price;
}
