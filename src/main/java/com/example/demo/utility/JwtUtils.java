package com.example.demo.utility;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.LoginDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	LoginDTO loginDTO;

	String tokenkey = "StoreBook";

	public String generateToken(LoginDTO loginDTO) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", loginDTO.getEmail());
		claims.put("password", loginDTO.getPassword());
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, tokenkey).compact();
	}

	public LoginDTO decodeToken(String token) {
		Map<String, Object> claims = new HashMap<>();
		claims = Jwts.parser().setSigningKey(tokenkey).parseClaimsJws(token).getBody();

		loginDTO.setEmail((String) claims.get("email"));
		loginDTO.setPassword((String) claims.get("password"));

		return loginDTO;
	}
}
