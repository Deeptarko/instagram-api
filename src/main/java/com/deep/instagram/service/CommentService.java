package com.deep.instagram.service;

import com.deep.instagram.exceptions.CommentException;
import com.deep.instagram.exceptions.PostException;
import com.deep.instagram.exceptions.UserException;
import com.deep.instagram.model.Comment;

public interface CommentService {
	
	public Comment createComment(Comment comment,Integer postId,Integer userId) throws UserException,PostException;
	public Comment findCommentById(Integer commentId) throws CommentException;
	public Comment likeComment(Integer commentId ,Integer userId) throws CommentException,UserException;
	public Comment unlikeComment(Integer commentId ,Integer userId) throws CommentException,UserException;
	
}
