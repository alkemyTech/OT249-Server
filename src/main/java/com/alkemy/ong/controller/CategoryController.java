package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.service.CategoryService;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

import javax.validation.Valid;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/categories")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getPagedUsers(@RequestParam(defaultValue = "0", name = "page") int page,
                                           @RequestParam(defaultValue = "asc", name = "order") String order) {
        return ResponseEntity.ok( categoryService.getAllCategories(page, order) );
    }
  
    @PostMapping("/categories")
	public ResponseEntity<Category> crearCategoria(@Valid @RequestBody CategoryDto categoryDto) {
		UUID uuid = UUID.randomUUID();
		boolean deleted = false;
		Category category = new Category(uuid, categoryDto.getName(), categoryDto.getDescription(), categoryDto.getImage(), new Timestamp(System.currentTimeMillis()), deleted);
		return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.OK);
	}
  
	  @GetMapping("/categories/{id}")
	  @PreAuthorize("hasRole('ROLE_ADMIN')")
	  public ResponseEntity<CategoryDto> getCategoryDetails(@PathVariable("id") UUID id){
	  	try {
	  		Category category = categoryService.getCategory(id);
	  		return ResponseEntity.ok(modelMapper.map(category, CategoryDto.class));
	  	} catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		    }
	  }
}
