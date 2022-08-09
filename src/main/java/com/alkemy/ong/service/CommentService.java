package com.alkemy.ong.service;

import java.util.List;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.CreateCommentDto;

import com.alkemy.ong.model.Comment;

public interface CommentService {
	
	public List<CommentDto> getAll();
	public boolean deleted(String id);
	public CreateCommentDto create(CreateCommentDto comment);
	public Comment findById(String id);
	public Comment actualizarComment(Comment comment);
	public List<Comment> commentsByPost(String id);
}
