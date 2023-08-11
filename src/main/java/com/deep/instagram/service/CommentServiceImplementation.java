package com.deep.instagram.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deep.instagram.dto.UserDto;
import com.deep.instagram.exceptions.CommentException;
import com.deep.instagram.exceptions.PostException;
import com.deep.instagram.exceptions.UserException;
import com.deep.instagram.model.Comment;
import com.deep.instagram.model.Post;
import com.deep.instagram.model.User;
import com.deep.instagram.repository.CommentRespository;
import com.deep.instagram.repository.PostRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImplementation implements CommentService {
	
	
	private final CommentRespository commentRespository;

	private final UserService userService;
	
	private final PostService postService;
	
	private final PostRepository postRepository;
	
	
	@Override
	public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException {
		
		User user=userService.findById(userId);
		Post post=postService.findPostById(postId);
		
		UserDto userDto=new UserDto();
		userDto.setEmail(user.getEmail());
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setUserImage(user.getImage());
		userDto.setUsername(user.getUsername());
		
		comment.setUser(userDto);
		comment.setCreatedAt(LocalDateTime.now());
		
		Comment createdComment= commentRespository.save(comment);
		post.getComments().add(createdComment);
		postRepository.save(post);
		
		return createdComment;
	}

	@Override
	public Comment findCommentById(Integer commentId) throws CommentException {
		
		Optional<Comment>opt=commentRespository.findById(commentId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
	
		throw new CommentException("Comment does not exist");
	}

	@Override
	public Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException {
		
		User user=userService.findById(userId);
		Comment comment=findCommentById(commentId);
		
		UserDto userDto=new UserDto();
		userDto.setEmail(user.getEmail());
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setUserImage(user.getImage());
		userDto.setUsername(user.getUsername());
		
		comment.getLikedByUsers().add(userDto);
		
		commentRespository.save(comment);
		
		return comment;
	}

	@Override
	public Comment unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException {
		User user=userService.findById(userId);
		Comment comment=findCommentById(commentId);
		
		UserDto userDto=new UserDto();
		userDto.setEmail(user.getEmail());
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setUserImage(user.getImage());
		userDto.setUsername(user.getUsername());
		
		comment.getLikedByUsers().remove(userDto);
		
		commentRespository.save(comment);
		
		return comment;
	}

}
