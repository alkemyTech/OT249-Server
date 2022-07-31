package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CategoryService {
    Category getCategory(String id);
    List<Category> getAllCategories();
    //Metodo necesitado
    Category createCategory(Category category);
    void deleteCategory(String id);
    void updateCategory(CategoryDto category, String id);

    Page<Map<String, String>> getAllCategories(int page, String order);
}
