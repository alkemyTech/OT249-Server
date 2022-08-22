package com.alkemy.ong.service.impl;


import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.CreateCommentDto;
import com.alkemy.ong.exceptions.RecordException.RecordNotFoundException;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.News;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.CommentService;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private NewsRepository newsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<CommentDto> getAll() {
		List<Comment> commentList = commentRepository.findAllByOrderByTimestampDesc();
		return commentList.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public boolean deleted(String id) {
		try {
			commentRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public CreateCommentDto create(CreateCommentDto comment) {
		News newFound = newsRepository.findById(comment.getNews()).orElseThrow(() -> new RecordNotFoundException("No se encontró la novedad"));
		User userFound = userRepository.findById(comment.getUser()).orElseThrow(() -> new RecordNotFoundException("No se encontró el usuario"));
		Comment commentCreated = new Comment();
		commentCreated.setNews(newFound);
		commentCreated.setUser(userFound);
		commentCreated.setBody(comment.getBody());
		commentCreated.setDeleted(false);
		commentCreated.setTimestamp(new Timestamp(System.currentTimeMillis()));
		commentRepository.save(commentCreated);
		return comment;
	}

	@Override
	@Transactional(readOnly = true)
	public Comment findById(String id) {
		Optional<Comment> commentOptional = commentRepository.findById(id);
		if (commentOptional.isPresent()) {
			return commentOptional.get();
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public Comment actualizarComment(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Comment> commentsByPost(String id) {
		Optional<News> newsOptional = newsRepository.findById(id);
		if (newsOptional.isPresent()) {
			News news = newsOptional.get();
			return commentRepository.findByNews(news);
		} else {
			return Collections.emptyList();
		}
	}

}
