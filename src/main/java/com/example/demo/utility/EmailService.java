package com.example.demo.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
	
	@Autowired
    private JavaMailSender javaMail;

    @Value(value = "srushtimanedeshmukh@gmail.com")
    private String sender;

    public String sendMail(String email, String mailBody){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setText(mailBody);
        javaMail.send(simpleMailMessage);
        return "mail send successfully";
    }
}
