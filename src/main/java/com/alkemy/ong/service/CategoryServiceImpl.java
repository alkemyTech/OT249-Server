package com.alkemy.ong.service;

import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;

    @Override
    public Category getCategory(Long id) {
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return null;
    }

	@Override
	@Transactional
	public Category createCategory(Category category) {
		return categoryRepository.save(category);
	}
}
