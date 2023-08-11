package com.deep.instagram.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deep.instagram.exceptions.UserException;
import com.deep.instagram.model.User;
import com.deep.instagram.repository.UserRespository;
import com.deep.instagram.response.MessageResponse;
import com.deep.instagram.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("id/{id}")
	public ResponseEntity<User> findUserByIdHandler(@PathVariable Integer id) throws UserException{
		User user=userService.findById(id);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@GetMapping("username/{userName}")
	public ResponseEntity<User> findUserByUsernameHandler(@PathVariable String userName) throws UserException{
		
		User user=userService.findUserByUsername(userName);
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
	}
	
	@PutMapping("follow/{followUserId}")
	public ResponseEntity<MessageResponse> followUserHandler(@PathVariable Integer followUserId,@RequestHeader("Authorization")String token) throws UserException{
		
		User user=userService.findUserByProfile(token);
		String message=userService.followUser(user.getId(), followUserId);
		
		MessageResponse response=new MessageResponse(message);
		
		
		return new ResponseEntity<MessageResponse>(response,HttpStatus.OK);
 
	
		
	}
	
	
	@PutMapping("unfollow/{unFollowUserId}")
	public ResponseEntity<MessageResponse> unFollowUserHandler(@PathVariable Integer unFollowUserId,@RequestHeader("Authorization")String token) throws UserException{
		
		User user=userService.findUserByProfile(token);
		String message=userService.unFollowUser(user.getId(), unFollowUserId);
		
		MessageResponse response=new MessageResponse(message);
		
		
		return new ResponseEntity<MessageResponse>(response,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/req")
	public ResponseEntity<User> findUserProfileHandler(@RequestHeader("Authorization")String token) throws UserException{
		
		User user=userService.findUserByProfile(token);
		
    
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	
	@GetMapping("/m/{userIds}")
	public ResponseEntity<List<User>> findUserByUserIdsHandler(@PathVariable List<Integer> userIds) throws UserException{
		
		List<User>users=userService.findUserByIds(userIds);
		
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<User>> searchUserHandler(@RequestParam("q") String query ) throws UserException{
		
		List<User>users=userService.searchUser(query);
		
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		
	}
	
	@PutMapping("/accounts/edit")
	public ResponseEntity<User> updateUserHandler(@RequestHeader("Authorization")String token,@RequestBody User user) throws UserException{
		
		User reqUser=userService.findUserByProfile(token);
		
		User updatedUser=userService.updateUserDetails(user, reqUser);
		
		return new ResponseEntity<User>(updatedUser,HttpStatus.OK);
		
	}
	
}
