package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategory(String id);
    List<Category> getAllCategories();
    //Metodo necesitado
    Category createCategory(Category category);
    void deleteCategory(String id);
    void updateCategory(CategoryDto category, String id);

    PageDto<?> getAllCategories(int page, String order);
}
