package com.deep.instagram.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deep.instagram.dto.UserDto;
import com.deep.instagram.exceptions.UserException;
import com.deep.instagram.model.User;
import com.deep.instagram.repository.UserRespository;
import com.deep.instagram.security.JwtTokenClaims;
import com.deep.instagram.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

	
	private final UserRespository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider tokenProvider;

	@Override
	public User registerUser(User user) throws UserException {

		Optional<User> isEmailExist = userRepository.findByEmail(user.getEmail());

		if (isEmailExist.isPresent()) {
			throw new UserException("User already exists");
		}

		Optional<User> isUsernameExist = userRepository.findByEmail(user.getUsername());

		if (isUsernameExist.isPresent()) {
			throw new UserException("UserName already exists");
		}

		if (user.getEmail() == null || user.getPassword() == null || user.getUsername() == null
				|| user.getName() == null) {
			throw new UserException("All fields are mandatory");
		}

		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setUsername(user.getUsername());
		newUser.setName(user.getName());

		return userRepository.save(newUser);
	}

	@Override
	public User findById(Integer id) throws UserException {

		Optional<User> opt = userRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}

		throw new UserException("User does not exist with id " + id);

	}

	@Override
	public User findUserByUsername(String username) throws UserException {
		Optional<User> optUser=userRepository.findByUsername(username);
		if(optUser.isPresent()) {
			return optUser.get();
		}
		throw new UserException("User not found with username "+username);
	}

	@Override
	public User findUserByProfile(String token) throws UserException {
		token=token.substring(7);
		JwtTokenClaims jwtTokenClaims=tokenProvider.getClaimsFromToken(token);
		
		String username=jwtTokenClaims.getUsername();
		
		Optional<User>opt=userRepository.findByEmail(username);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new UserException("Invalid Token");
		
	}

	@Override
	public String followUser(Integer reqUserId, Integer followUserId) throws UserException {
		// TODO Auto-generated method stub
		User reqUser = findById(reqUserId);
		User followUser = findById(followUserId);

		UserDto follower = new UserDto();
		follower.setEmail(reqUser.getEmail());
		follower.setUsername(reqUser.getUsername());
		follower.setName(reqUser.getName());
		follower.setUserImage(reqUser.getImage());
		follower.setId(reqUser.getId());

		UserDto following = new UserDto();
		following.setEmail(followUser.getEmail());
		following.setId(followUser.getId());
		following.setUserImage(followUser.getImage());
		following.setName(followUser.getName());
		following.setUsername(followUser.getUsername());
		
		reqUser.getFollowing().add(following);
		followUser.getFollower().add(follower);
		
		userRepository.save(followUser);
		userRepository.save(reqUser);
		
		return "You are following "+followUser.getUsername();
	}

	@Override
	public String unFollowUser(Integer reqUserId, Integer followUserId) throws UserException {
		User reqUser = findById(reqUserId);
		User followUser = findById(followUserId);

		UserDto follower = new UserDto();
		follower.setEmail(reqUser.getEmail());
		follower.setUsername(reqUser.getUsername());
		follower.setName(reqUser.getName());
		follower.setUserImage(reqUser.getImage());
		follower.setId(reqUser.getId());

		UserDto following = new UserDto();
		following.setEmail(follower.getEmail());
		following.setId(follower.getId());
		following.setUserImage(follower.getUserImage());
		following.setName(follower.getName());
		following.setUsername(follower.getUsername());
		
		reqUser.getFollowing().remove(following);
		followUser.getFollower().remove(follower);
		
		userRepository.save(followUser);
		userRepository.save(reqUser);
		return "You have unfollowed "+followUser.getUsername();
	}

	@Override
	public List<User> findUserByIds(List<Integer> userIds) throws UserException {
		List<User>users=userRepository.findAllUsersByUserIds(userIds);
		
		return users;
	}

	@Override
	public List<User> searchUser(String query) throws UserException {
		List<User>users=userRepository.findByQuery(query);
		
		if(users.size()==0) {
			throw new UserException("User not found");
		}
		
		return users;
	}

	@Override
	public User updateUserDetails(User updateUser, User existingUser) throws UserException {
		
		if(updateUser.getEmail()!=null) {
			existingUser.setEmail(updateUser.getEmail());
		}
		if(updateUser.getBio()!=null) {
			existingUser.setBio(updateUser.getBio());
		}
		
		if(updateUser.getName()!=null) {
			existingUser.setName(updateUser.getName());
		}
		if(updateUser.getUsername()!=null) {
			existingUser.setUsername(updateUser.getUsername());
		}
		
		if(updateUser.getGender()!=null) {
			existingUser.setGender(updateUser.getGender());
		}
		
		
		if(updateUser.getMobile()!=null) {
			existingUser.setMobile(updateUser.getMobile());
		}
		if(updateUser.getWebsite()!=null) {
			existingUser.setWebsite(updateUser.getWebsite());
			
		}
		if(updateUser.getImage()!=null) {
			existingUser.setImage(updateUser.getImage());
			
		}
		if(updateUser.getId().equals(existingUser.getId())) {
			return userRepository.save(existingUser);
		}
		
		throw new UserException("You cant update this user");
	}

}
