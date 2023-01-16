package com.example.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class UserConfig {

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

	  @Bean
	  JavaMailSender javaMailSender() {
	   return new JavaMailSenderImpl();
	    }
	 
	
}
