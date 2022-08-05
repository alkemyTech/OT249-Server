package com.alkemy.ong.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<CommentDto> getAll() {
		List<Comment> commentList = commentRepository.findAllByOrderByTimestampDesc();
		return commentList.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
	}

	@Override
	public Comment deleted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment create() {
		// TODO Auto-generated method stub
		return null;
	}

}
