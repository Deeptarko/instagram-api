package com.deep.instagram.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deep.instagram.dto.UserDto;
import com.deep.instagram.exceptions.PostException;
import com.deep.instagram.exceptions.UserException;
import com.deep.instagram.model.Post;
import com.deep.instagram.model.User;
import com.deep.instagram.repository.PostRepository;
import com.deep.instagram.repository.UserRespository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PostServiceImplementation implements PostService {
	
	private final PostRepository postRepository;
	private final UserService userService;
	private final UserRespository userRespository;
	
	@Override
	public Post createPost(Post post, Integer userId) throws UserException {
		User user=userService.findById(userId);
		UserDto userDto=new UserDto();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setUserImage(user.getImage());
		userDto.setUsername(user.getUsername());
		
		post.setUser(userDto);
		Post createdPost=postRepository.save(post);
		
		
		return createdPost;
	}

	@Override
	public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
		Post post=findPostById(postId);
		
		User user=userService.findById(userId);
		
		if(post.getUser().getId().equals(user.getId())) {
			postRepository.deleteById(post.getId());
			
			return "Post deleted successfully";
		}
		throw new PostException("You cant delete other people posts");
	}

	@Override
	public List<Post> findPostByUserId(Integer userId) throws UserException {
		List<Post>posts=postRepository.findByUserId(userId);
		if(posts.size()==0) {
			throw new UserException("This user does not have nay post");
		}
		return posts;
	}

	@Override
	public Post findPostById(Integer postId) throws PostException {
		
		Optional<Post>opt=postRepository.findById(postId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new PostException("Post not found with the given post id");
		
		
	}

	@Override
	public List<Post> findAllPostByUserIds(List<Integer> userIds) throws UserException, PostException {
		
		List<Post>posts=postRepository.findAllPostByUserIds(userIds);
		if(posts.size()==0) {
			throw new PostException("No Post Available");
		}
		
		return posts;
	}

	@Override
	public String savedPost(Integer postId, Integer userId) throws PostException, UserException {
		
		Post post=findPostById(postId);
		User user=userService.findById(userId);
		
		if(!user.getSavedPost().contains(post)) {
			user.getSavedPost().add(post);
			userRespository.save(user);
		}
		
		return "Post saved successfully";
	}

	@Override
	public String unSavedPost(Integer postId, Integer userId) throws PostException, UserException {
		Post post=findPostById(postId);
		User user=userService.findById(userId);
		
		if(!user.getSavedPost().contains(post)) {
			user.getSavedPost().remove(post);
			userRespository.save(user);
		}
		
		return "Post removed successfully";
	}

	@Override
	public Post likePost(Integer postId, Integer userId) throws PostException, UserException {
		
		Post post=findPostById(postId);
		User user=userService.findById(userId);
		
		UserDto userDto=new UserDto();
		
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setUserImage(user.getImage());
		userDto.setUsername(user.getUsername());
		
		post.getLikedByUsers().add(userDto);
		
		return postRepository.save(post);
	}

	@Override
	public Post unlikePost(Integer postId, Integer userId) throws PostException, UserException {
		Post post=findPostById(postId);
		User user=userService.findById(userId);
		
		UserDto userDto=new UserDto();
		
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setUserImage(user.getImage());
		userDto.setUsername(user.getUsername());
		
		post.getLikedByUsers().remove(userDto);
		
		return postRepository.save(post);
	}

}
