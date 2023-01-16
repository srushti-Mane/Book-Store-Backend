package com.example.demo.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderDTO {

	private String firstName;
    private String lastName;
    private String phoneNo;
    private String pinCode;
    private String locality;
    private String address;
    private String city;
    private String landMark;
    private String AddressType;
    private LocalDate orderDate = LocalDate.now();
}
