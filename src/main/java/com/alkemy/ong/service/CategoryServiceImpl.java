package com.alkemy.ong.service;

import com.alkemy.ong.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{


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
}
