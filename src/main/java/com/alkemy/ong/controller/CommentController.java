package com.alkemy.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.CreateCommentDto;
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
	public ResponseEntity<CreateCommentDto> createComment(@RequestBody CreateCommentDto comment){
		return ResponseEntity.ok(commentService.create(comment));
	}
}
