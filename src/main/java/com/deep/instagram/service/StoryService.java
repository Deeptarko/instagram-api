package com.deep.instagram.service;

import java.util.List;

import com.deep.instagram.exceptions.StoryException;
import com.deep.instagram.exceptions.UserException;
import com.deep.instagram.model.Story;

public interface StoryService {
	public Story createStory(Story story,Integer userId) throws UserException;
	public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;
	
}
