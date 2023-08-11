package com.deep.instagram.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deep.instagram.model.User;
import com.deep.instagram.repository.UserRespository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRespository userRespository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User>opt=userRespository.findByEmail(username);
		
		if(opt.isPresent()) {
			
			User user=opt.get();
			List<GrantedAuthority>authorities=new ArrayList<>();
			
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
			
			
		}
		throw new BadCredentialsException("Bad Credentials ...");
	}

}
