package com.alkemy.ong.service;

import java.util.Optional;

import com.alkemy.ong.model.Comment;

public interface CommentService {
	
	public Optional<Comment> getAll();
	public Comment deleted();
	public Comment update();
	public Comment create();
}
