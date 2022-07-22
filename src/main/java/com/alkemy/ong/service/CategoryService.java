package com.alkemy.ong.service;

import com.alkemy.ong.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    Category getCategory(Long id);
    List<Category> getAllCategories();
    void deleteCategory(Long id);
    Category updateCategory(Category category, Long id);

    Page<Map<String, String>> getAllCategories(int page, String order);
}
