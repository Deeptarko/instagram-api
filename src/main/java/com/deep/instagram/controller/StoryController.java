package com.deep.instagram.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deep.instagram.exceptions.StoryException;
import com.deep.instagram.exceptions.UserException;
import com.deep.instagram.model.Story;
import com.deep.instagram.model.User;
import com.deep.instagram.service.StoryService;
import com.deep.instagram.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
public class StoryController {
	
	private final UserService userService;
	private final StoryService storyService;
	
	@PostMapping("/create")
	public ResponseEntity<Story> createStoryHandler(@RequestBody Story story,@RequestHeader("Authorization")String token) throws UserException{
		
		User user=userService.findUserByProfile(token);
		Story createdStory=storyService.createStory(story, user.getId());
		
		return new ResponseEntity<Story>(createdStory,HttpStatus.OK);
	}
	
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<Story>> findAllStoryByUserIdHandler(@PathVariable Integer userId) throws UserException, StoryException{
		
		User user=userService.findById(userId);
		
		List<Story>stories=storyService.findStoryByUserId(userId);
		
		return new ResponseEntity<List<Story>>(stories,HttpStatus.OK);
	}
	
	
}
