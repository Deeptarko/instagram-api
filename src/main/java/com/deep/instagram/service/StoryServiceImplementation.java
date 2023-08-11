package com.deep.instagram.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deep.instagram.dto.UserDto;
import com.deep.instagram.exceptions.StoryException;
import com.deep.instagram.exceptions.UserException;
import com.deep.instagram.model.Story;
import com.deep.instagram.model.User;
import com.deep.instagram.repository.StoryRepository;
import com.deep.instagram.repository.UserRespository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryServiceImplementation implements StoryService{
	
	private final StoryRepository storyRepository;
	private final UserService userService;
	private final UserRespository userRespository;
	
	public Story createStory(Story story,Integer userId) throws UserException {
		
		User user=userService.findById(userId);
		
		UserDto userDto=new UserDto();
		
		userDto.setEmail(user.getEmail());
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setUserImage(user.getImage());
		userDto.setUsername(user.getUsername());
		
		story.setUser(userDto);
		story.setTimestamp(LocalDateTime.now());
		user.getStories().add(story);
		
		userRespository.save(user);
		
		return storyRepository.save(story);
		
		
	}
	
	
	public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException{
		
		User user=userService.findById(userId);
		List<Story>stories=user.getStories();
		
		if(stories.size()==0) {
			throw new StoryException("This user does not have any story");
		}
		
		
		return stories;
		
	}
	
}
