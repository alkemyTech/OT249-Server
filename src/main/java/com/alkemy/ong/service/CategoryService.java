package com.alkemy.ong.service;

import com.alkemy.ong.model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategory(Long id);
    List<Category> getAllCategories();
    void deleteCategory(Long id);
    Category updateCategory(Category category, Long id);
    Category createCategory(Category category);
}
