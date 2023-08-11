package com.deep.instagram.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptions {
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails>UserExceptionHandler(UserException e,WebRequest req){
		
		ErrorDetails err=new ErrorDetails(e.getMessage(),req.getDescription(false),LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(PostException.class)
	public ResponseEntity<ErrorDetails>PostExceptionHandler(PostException e,WebRequest req){
		
		ErrorDetails err=new ErrorDetails(e.getMessage(),req.getDescription(false),LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CommentException.class)
	public ResponseEntity<ErrorDetails>CommentExceptionHandler(CommentException e,WebRequest req){
		
		ErrorDetails err=new ErrorDetails(e.getMessage(),req.getDescription(false),LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails>MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e,WebRequest req){
		
		ErrorDetails err=new ErrorDetails(e.getBindingResult().getFieldError().getDefaultMessage(),"Validation Error",LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails>otherExceptionHandler(UserException e,WebRequest req){
		
		ErrorDetails err=new ErrorDetails(e.getMessage(),req.getDescription(false),LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}

}
