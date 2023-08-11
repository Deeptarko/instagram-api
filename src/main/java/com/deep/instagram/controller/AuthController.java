package com.deep.instagram.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deep.instagram.exceptions.UserException;
import com.deep.instagram.model.User;
import com.deep.instagram.repository.UserRespository;
import com.deep.instagram.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	
	private final UserService userService;
	private final UserRespository userRespository;
	
	@PostMapping("/signup")
	public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException{
		
		User createdUser=userService.registerUser(user);
		return new ResponseEntity<User>(createdUser,HttpStatus.OK);
		
	}
	
	@GetMapping("/signin")
	public ResponseEntity<User> signInHandler(Authentication auth ) throws UserException{
		
		Optional<User> opt=userRespository.findByEmail(auth.getName());
		if(opt.isPresent()) {
			return new ResponseEntity<User>(opt.get(),HttpStatus.ACCEPTED);
		}
		
		throw new BadCredentialsException("Invalid username or password");
		
	}
}
