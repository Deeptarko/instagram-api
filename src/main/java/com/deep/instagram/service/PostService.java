package com.deep.instagram.service;

import java.util.List;

import com.deep.instagram.exceptions.PostException;
import com.deep.instagram.exceptions.UserException;
import com.deep.instagram.model.Post;

public interface PostService {
	
	public Post createPost(Post post,Integer userId) throws UserException;
	
	public String deletePost(Integer postId,Integer userId) throws UserException,PostException;
	
	public List<Post> findPostByUserId(Integer userId) throws UserException;
	
	public Post findPostById(Integer postId) throws PostException;
	
	public List<Post> findAllPostByUserIds(List<Integer>userIds) throws UserException,PostException;
	
	public String savedPost(Integer postId,Integer userId) throws PostException,UserException;
	
	public String unSavedPost(Integer postId,Integer userId) throws PostException,UserException;
	
	public Post likePost(Integer postId,Integer userId) throws PostException,UserException;
	
	public Post unlikePost(Integer postId,Integer userId) throws PostException,UserException;
	
	
	
	
}