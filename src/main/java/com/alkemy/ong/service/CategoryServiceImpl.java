package com.alkemy.ong.service;

import com.alkemy.ong.Utils.PageUtils;
import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    
	@PersistenceContext
	private EntityManager entityManager;

    @Override
    public Category getCategory(UUID id) {
        return categoryRepository.getById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }

    @Override
    public void deleteCategory(UUID id) {
    	
    	String queryFind = "FROM Category a WHERE a.id=" + id;

    	Category categoryAux = (Category) entityManager.createQuery(queryFind).getSingleResult();
    	
    	categoryAux.setDeleted(true);
    	
    	String queryDeleted = "UPDATE categories SET deleted=1 WHERE Id = " + id;
    	
    	entityManager.createNativeQuery(queryDeleted).executeUpdate();
    }

    @Override
    public Category updateCategory(Category category, UUID id) {
        return null;
    }


    @Override
    public Page<Map<String, String>> getAllCategories(int page, String order) {
        Page<Category> category = categoryRepository.findAll( PageUtils.getPageable( page, order ) );
        return category.map(cat -> Map.of("name", modelMapper.map( cat, CategoryDto.class).getName()));
    }
}
