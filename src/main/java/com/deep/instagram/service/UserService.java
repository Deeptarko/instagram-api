package com.deep.instagram.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deep.instagram.exceptions.UserException;
import com.deep.instagram.model.User;

public interface UserService{
	
	public User registerUser(User user) throws UserException;
	public User findById(Integer id) throws UserException;
	public User findUserByUsername(String username) throws UserException;
	public User findUserByProfile(String token) throws UserException;
	public String followUser(Integer reqUserId,Integer followUserId) throws UserException;
	public String unFollowUser(Integer reqUserId,Integer followUserId) throws UserException;
	
	public List<User> findUserByIds(List<Integer>userIds) throws UserException;
	
	public List<User> searchUser(String query) throws UserException;
	
	public User updateUserDetails (User updateUser,User existingUser) throws UserException;
	
	
	
	

}
