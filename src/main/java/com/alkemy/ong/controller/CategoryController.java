package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    
	private CategoryRepository categoryRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPagedUsers(@RequestParam(defaultValue = "0", name = "page") int page,
                                           @RequestParam(defaultValue = "asc", name = "order") String order) {
        return ResponseEntity.ok( categoryService.getAllCategories(page, order) );
    }
  
    //Metodo funcionando
    @PostMapping("/categories")
	public ResponseEntity<Category> crearCategoria(@Valid @RequestBody CategoryDto categoryDto) {
		boolean deleted = false;
		Category category = new Category(categoryDto.getName(), categoryDto.getDescription(), categoryDto.getImage(), new Timestamp(System.currentTimeMillis()), deleted);
		return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.OK);
	}
  
	  @GetMapping("/categories/{id}")
	  @PreAuthorize("hasRole('ROLE_ADMIN')")
	  public ResponseEntity<CategoryDto> getCategoryDetails(@PathVariable("id") String id){
	  	try {
	  		Category category = categoryService.getCategory(id);
	  		return ResponseEntity.ok(modelMapper.map(category, CategoryDto.class));
	  	} catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		    }
	  }
	  	
		@DeleteMapping("/categories/{id}")
	  	@PreAuthorize("hasRole('ROLE_ADMIN')")
		public ResponseEntity<String> deleteCategory(@PathVariable("id") String id) {

			if (categoryRepository.existsById(id)) {
				categoryService.deleteCategory(id);

				return new ResponseEntity<>("Borrado con éxito", HttpStatus.NO_CONTENT);
			} else {

				return new ResponseEntity<>("Categoría no encontrada", HttpStatus.NOT_FOUND);
			}

		}
		
		@PutMapping("/categories/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<String> updateCategory(@PathVariable("id") String id,@RequestBody CategoryDto category){
			
			if (categoryRepository.existsById(id)) {
				categoryService.updateCategory(category, id);

				return new ResponseEntity<>("Categoria actualizada con éxito", HttpStatus.OK);
			} else {

				return new ResponseEntity<>("Categoría no encontrada", HttpStatus.NOT_FOUND);
			}
			
			
		}
	  
}
