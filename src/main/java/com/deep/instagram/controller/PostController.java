package com.deep.instagram.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deep.instagram.exceptions.PostException;
import com.deep.instagram.exceptions.UserException;
import com.deep.instagram.model.Post;
import com.deep.instagram.model.User;
import com.deep.instagram.response.MessageResponse;
import com.deep.instagram.service.PostService;
import com.deep.instagram.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
	
	private final PostService postService;
	private final UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Post> createPostHandler(@RequestBody Post post,@RequestHeader("Authorization")String token) throws UserException{
		User user=userService.findUserByProfile(token);
		Post createdPost=postService.createPost(post, user.getId());
		
		return new ResponseEntity<Post>(createdPost,HttpStatus.OK);
		
	}
	
	@GetMapping("/all/{id}")
	public ResponseEntity<List<Post>> findPostByUserIdHandler(@PathVariable("id") Integer userId) throws UserException{
		
		
		List<Post>posts=postService.findPostByUserId(userId);
		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/following/{ids}")
	public ResponseEntity<List<Post>> findAllPostByUserIdsHandler(@PathVariable("ids") List<Integer>userIds) throws UserException, PostException{
		
		
		List<Post>posts=postService.findAllPostByUserIds(userIds);
		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws UserException, PostException{
		
		
		Post post=postService.findPostById(postId);
		
		return new ResponseEntity<Post>(post,HttpStatus.OK);
	}
	
	@PutMapping("/like/{postId}")
	public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId,@RequestHeader("Authorization")String token) throws UserException, PostException{
		
		User user=userService.findUserByProfile(token);
		Post likedPost=postService.likePost(postId, user.getId());
		
		return new ResponseEntity<Post>(likedPost,HttpStatus.OK);
	}
	
	@PutMapping("/unlike/{postId}")
	public ResponseEntity<Post> unlikePostHandler(@PathVariable Integer postId,@RequestHeader("Authorization")String token) throws UserException, PostException{
		
		User user=userService.findUserByProfile(token);
		Post unlikedPost=postService.unlikePost(postId, user.getId());
		
		return new ResponseEntity<Post>(unlikedPost,HttpStatus.OK);
	}
	 
	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<MessageResponse>deletePostHandler(@PathVariable Integer postId,@RequestHeader("Authorization")String token) throws UserException, PostException{
		User user=userService.findUserByProfile(token);
		String message=postService.deletePost(postId, user.getId());
		
		MessageResponse response=new MessageResponse(message);
		
		return new ResponseEntity<MessageResponse>(response,HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/save_post/{postId}")
	public ResponseEntity<MessageResponse>savedPostHandler(@PathVariable Integer postId,@RequestHeader("Authorization")String token) throws UserException, PostException{
		User user=userService.findUserByProfile(token);
		String message=postService.savedPost(postId, user.getId());
		
		MessageResponse response=new MessageResponse(message);
		
		return new ResponseEntity<MessageResponse>(response,HttpStatus.ACCEPTED);
	}
	
}
