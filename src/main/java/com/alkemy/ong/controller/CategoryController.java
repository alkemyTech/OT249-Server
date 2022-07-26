package com.alkemy.ong.controller;

import com.alkemy.ong.service.CategoryService;
import com.alkemy.ong.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

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
}
