package com.alkemy.ong.service.impl;

import com.alkemy.ong.utils.PageUtils;
import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        return categoryRepository.findById(id).get();
    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }

    @Override
    @Transactional
    public void deleteCategory(String id) {

    	Category category = categoryRepository.findById(id).get();
    	categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public void updateCategory(CategoryDto category, String id) {
       
    	Category categoryAux = entityManager.find(Category.class, id);
    	
    	categoryAux.setName(category.getName());
    	categoryAux.setDescription(category.getDescription());
    	categoryAux.setImage(category.getImage());
    	
    	entityManager.merge(categoryAux);
    	
    	
    }

	@Override
	@Transactional
	//Metodo necesitado
	public Category createCategory(Category category) {
		return categoryRepository.save(category);
	}

    @Override
    public PageDto<Map<String, String>> getAllCategories(int page, String order) {
        Page<Category> category = categoryRepository.findAll( PageUtils.getPageable( page, order ) );
        Page<Map<String, String>> categoryDto = category.map( cat -> Map.of( "name", modelMapper.map( cat, CategoryDto.class ).getName()) );
        return PageUtils.getPageDto(categoryDto, "categories");
    }

}
