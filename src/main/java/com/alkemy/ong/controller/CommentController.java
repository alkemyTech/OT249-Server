package com.alkemy.ong.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.CreateCommentDto;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.service.CommentService;

@RestController
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/comments")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllComments(){
		return ResponseEntity.ok(commentService.getAll());
	}
	
	@PostMapping("/comments")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<CreateCommentDto> createComment(@Valid @RequestBody CreateCommentDto comment){
		return ResponseEntity.ok(commentService.create(comment));
	}
	
	@PutMapping("/comments/{id}")
	public ResponseEntity<Comment> actualizarComment(@PathVariable("id") String id, @RequestBody CreateCommentDto commentDto) {
		Comment commentEncontrado = commentService.findById(id);
		if (commentEncontrado == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			commentEncontrado.setBody(commentDto.getBody());
			return new ResponseEntity<>(commentService.actualizarComment(commentEncontrado), HttpStatus.OK);
		} catch (DataAccessException ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
