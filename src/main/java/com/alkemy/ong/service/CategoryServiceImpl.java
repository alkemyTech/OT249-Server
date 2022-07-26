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

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategory(UUID id) {
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }

    @Override
    public void deleteCategory(UUID id) {

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
