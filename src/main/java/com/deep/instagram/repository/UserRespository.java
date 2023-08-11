package com.deep.instagram.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deep.instagram.model.User;

public interface UserRespository extends JpaRepository<User,Integer>{
	
	public Optional<User> findByUsername(String username);
	public Optional<User> findByEmail(String email);
	
	
	@Query("SELECT u From User u Where u.id IN :users")
	public List<User> findAllUsersByUserIds(@Param("users") List<Integer>userIds);
	
	@Query("SELECT DISTINCT u from User u Where u.username LIKE %:query% OR u.email LIKE %:query%")
	public List<User>findByQuery(@Param("query") String query);

}
