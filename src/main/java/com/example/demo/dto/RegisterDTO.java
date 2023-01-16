package com.example.demo.dto;

import com.example.demo.model.CartModel;

import lombok.Data;

@Data
public class RegisterDTO {

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String phoneNo;
	
	private String pinCode;

	private String locality;

	private String address;

	private String city;

	private String landMark;

	private String AddressType;

	private CartModel cartModel;
}
