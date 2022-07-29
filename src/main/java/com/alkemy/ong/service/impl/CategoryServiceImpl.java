package com.alkemy.ong.service.impl;

import com.alkemy.ong.Utils.PageUtils;
import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Category getCategory(String id) {
        return categoryRepository.getById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }

    @Override
    public void deleteCategory(String id) {
 	String queryDeleted = "UPDATE categories SET deleted=1 WHERE Id = '" + id + "'";
    	
    	entityManager.createNativeQuery(queryDeleted).executeUpdate();
    }

    @Override
    public Category updateCategory(Category category, String id) {
        return null;
    }

	@Override
	@Transactional
	//Metodo necesitado
	public Category createCategory(Category category) {
		return categoryRepository.save(category);
	}

    @Override
    public Page<Map<String, String>> getAllCategories(int page, String order) {
        Page<Category> category = categoryRepository.findAll( PageUtils.getPageable( page, order ) );
        return category.map(cat -> Map.of("name", modelMapper.map( cat, CategoryDto.class).getName()));
    }
}
