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

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
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
    public Page<CategoryDto> getAllCategories(int page, String order) {
        Page<Category> category = categoryRepository.findAll( PageUtils.getPageable( page, order ) );
        Page<CategoryDto> categorDtoPage = category.map( cat -> modelMapper.map( cat, CategoryDto.class));
        return categorDtoPage;
    }
}
