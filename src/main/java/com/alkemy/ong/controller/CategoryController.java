package com.alkemy.ong.controller;

import java.sql.Timestamp;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.service.CategoryService;

@RestController
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/categories")
	public ResponseEntity<Category> crearCategoria(@Valid @RequestBody CategoryDto categoryDto) {
		UUID uuid = UUID.randomUUID();
		boolean deleted = false;
		Category category = new Category(uuid, categoryDto.getName(), categoryDto.getDescription(), categoryDto.getImage(), new Timestamp(System.currentTimeMillis()), deleted);
		return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.OK);
	}
	
}
