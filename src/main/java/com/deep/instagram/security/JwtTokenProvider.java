package com.deep.instagram.security;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.deep.instagram.config.SecurityContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenProvider {
	
	public JwtTokenClaims  getClaimsFromToken(String token) {
		SecretKey key=Keys.hmacShaKeyFor(SecurityContext.JWT_KEY.getBytes());
		
		Claims claims=Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		
		String username=String.valueOf(claims.get("username"));
		
		return new JwtTokenClaims(username);
	}
}
