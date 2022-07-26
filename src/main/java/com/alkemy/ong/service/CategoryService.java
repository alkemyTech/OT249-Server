package com.alkemy.ong.service;

import com.alkemy.ong.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CategoryService {
    Category getCategory(UUID id);
    List<Category> getAllCategories();
    Category createCategory(Category category);
    void deleteCategory(UUID id);
    Category updateCategory(Category category, UUID id);
    Page<Map<String, String>> getAllCategories(int page, String order);
}
