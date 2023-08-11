package com.deep.instagram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deep.instagram.exceptions.CommentException;
import com.deep.instagram.exceptions.PostException;
import com.deep.instagram.exceptions.UserException;
import com.deep.instagram.model.Comment;
import com.deep.instagram.model.User;
import com.deep.instagram.service.CommentService;
import com.deep.instagram.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
	
	private final CommentService commentService;
	private final UserService userService;
	
	@PostMapping("/create/{postId}")
	public ResponseEntity<Comment> createCommentHandler(@RequestBody Comment comment,@PathVariable Integer postId,@RequestHeader("Authorization") String token) throws UserException, PostException{
		
		User user=userService.findUserByProfile(token);
		Comment createdComment=commentService.createComment(comment, postId, user.getId());
		
		return new ResponseEntity<Comment>(createdComment,HttpStatus.OK);
	}
	
	
	
	@PutMapping("/like/{commentId}")
	public ResponseEntity<Comment>likeCommentHandler(@RequestHeader("Authorization") String token,@PathVariable Integer commentId) throws UserException, CommentException{
		
		User user=userService.findUserByProfile(token);
		Comment comment=commentService.likeComment(commentId,user.getId());
		
		return new ResponseEntity<Comment>(comment,HttpStatus.OK);
	}
	

	@PutMapping("/unlike/{commentId}")
	public ResponseEntity<Comment>unlikeCommentHandler(@RequestHeader("Authorization") String token,@PathVariable Integer commentId) throws UserException, CommentException{
		
		User user=userService.findUserByProfile(token);
		Comment comment=commentService.unlikeComment(commentId,user.getId());
		
		return new ResponseEntity<Comment>(comment,HttpStatus.OK);
	}
	
	
	
}
