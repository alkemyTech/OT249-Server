package com.alkemy.ong.service;

import java.util.List;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.model.Comment;

public interface CommentService {
	
	public List<CommentDto> getAll();
	public Comment deleted();
	public Comment update();
	public Comment create();
}
