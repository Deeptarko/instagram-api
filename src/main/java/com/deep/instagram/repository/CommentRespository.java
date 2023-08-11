package com.deep.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deep.instagram.model.Comment;

public interface CommentRespository extends JpaRepository<Comment, Integer> {

}
