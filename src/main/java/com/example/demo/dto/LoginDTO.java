package com.example.demo.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class LoginDTO {

	 private String email;
	    private String password;
}
