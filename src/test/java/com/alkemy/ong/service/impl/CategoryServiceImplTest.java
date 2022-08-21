package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.utils.PageUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CategoryServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CategoryServiceImplTest {

    @MockBean
    private CategoryRepository categoryRepository;

    private CategoryServiceImpl categoryServiceImpl;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @SpyBean
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        categoryServiceImpl = new CategoryServiceImpl( modelMapper, categoryRepository, entityManager );
    }

    /**
     * Method under test: {@link CategoryServiceImpl#getCategory(String)}
     */
    @Test
    void test_GetCategory() {

        Category category = getCategory();
        Optional<Category> ofResult = Optional.of( category );
        when( categoryRepository.findById( any() ) ).thenReturn( ofResult );
        assertSame( category, categoryServiceImpl.getCategory( "42" ) );
        verify( categoryRepository ).findById( any() );
    }

    private static Category getCategory() {

        Category category = new Category();
        category.setDeleted( true );
        category.setDescription( "The characteristics of someone or something" );
        category.setId( "42" );
        category.setImage( "Image" );
        category.setName( "Name" );
        category.setTimestamp( mock( Timestamp.class ) );
        return category;
    }

    /**
     * Method under test: {@link CategoryServiceImpl#getAllCategories()}
     */
    @Test
    void test_GetAllCategories() {
        // TODO: Complete this test.
        // Act
        List<Category> actualAllCategories = categoryServiceImpl.getAllCategories();

        // Assert
        // TODO: Add assertions on result

        assertThat( actualAllCategories ).isNull();
    }

    /**
     * Method under test: {@link CategoryServiceImpl#getAllCategories(int, String)}
     */
    @Test
    void test_GetAllCategories2() {
        // TODO: Complete this test.

        // Arrange
        // TODO: Populate arranged inputs
        ArrayList<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setName( "Name" );
        categories.add( category );
        categories.add( category );
        when( categoryRepository.findAll( any( Pageable.class ) ) ).thenReturn( new PageImpl<>( categories, PageUtils.getPageable( 1, "asc" ), 1 ) );
        PageDto<Map<String, String>> allCategories = categoryServiceImpl.getAllCategories( 1, "asc" );

        assertThat( allCategories ).isNotNull();
    }

    /**
     * Method under test: {@link CategoryServiceImpl#deleteCategory(String)}
     */
    @Test
    void test_DeleteCategory() {

        Category category = getCategory();
        Optional<Category> ofResult = Optional.of( category );
        doNothing().when( categoryRepository ).delete( any() );
        when( categoryRepository.findById( any() ) ).thenReturn( ofResult );
        categoryServiceImpl.deleteCategory( "42" );
        verify( categoryRepository ).findById( any() );
        verify( categoryRepository ).delete( any() );
    }

    /**
     * Method under test: {@link CategoryServiceImpl#updateCategory(CategoryDto, String)}
     */
    @Test
    void test_UpdateCategory2() {

        Category category = getCategory();
        when( entityManager.merge( any() ) ).thenReturn( "Merge" );
        when( entityManager.find( any(), any() ) ).thenReturn( category );
        categoryServiceImpl.updateCategory( new CategoryDto(), "42" );
        verify( entityManager ).find( any(), any() );
        verify( entityManager ).merge( any() );
    }

    /**
     * Method under test: {@link CategoryServiceImpl#createCategory(Category)}
     */
    @Test
    void test_CreateCategory() {

        Category category = getCategory();
        when( categoryRepository.save( any() ) ).thenReturn( category );

        Category category1 = getCategory();
        assertSame( category, categoryServiceImpl.createCategory( category1 ) );
        verify( categoryRepository ).save( any() );
    }

}

