package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CategoryController.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CategoryControllerTest {

    private CategoryController categoryController;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private CategoryService categoryService;

    @SpyBean
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        categoryController = new CategoryController( categoryService, categoryRepository, modelMapper );
    }

    /**
     * Method under test: {@link CategoryController#crearCategoria(CategoryDto)}
     */
    @Test
    void test_CrearCategoria() throws Exception {

        Category category = getCategory( );
        when( categoryService.createCategory( any() ) ).thenReturn( category );

        CategoryDto categoryDto = getCategoryDto( );
        String content = (new ObjectMapper()).writeValueAsString( categoryDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/categories" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( categoryController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content()
                        .json(
                                "{\"id\":\"42\",\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"image\":\"Image\""
                                        + ",\"timestamp\":10,\"deleted\":true}" ) );
    }

    private static CategoryDto getCategoryDto() {
        Timestamp timestamp = mock( Timestamp.class );
        when( timestamp.getTime() ).thenReturn( 10L );

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setDeleted( true );
        categoryDto.setDescription( "The characteristics of someone or something" );
        categoryDto.setId( "42" );
        categoryDto.setImage( "Image" );
        categoryDto.setName( "Name" );
        categoryDto.setTimestamp( timestamp );
        return categoryDto;
    }

    private static Category getCategory() {

        Timestamp timestamp = mock( Timestamp.class );
        when( timestamp.getTime() ).thenReturn( 10L );

        Category category = new Category();
        category.setDeleted( true );
        category.setDescription( "The characteristics of someone or something" );
        category.setId( "42" );
        category.setImage( "Image" );
        category.setName( "Name" );
        category.setTimestamp( timestamp );
        return category;
    }

    /**
     * Method under test: {@link CategoryController#deleteCategory(String)}
     */
    @Test
    void test_DeleteCategory() throws Exception {

        doNothing().when( categoryService ).deleteCategory( anyString() );
        when( categoryRepository.existsById( anyString() ) ).thenReturn( true );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/categories/{id}", "42" );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( categoryController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isNoContent() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "Borrado con éxito" ) );
    }

    /**
     * Method under test: {@link CategoryController#deleteCategory(String)}
     */
    @Test
    void test_DeleteCategory2() throws Exception {

        doNothing().when( categoryService ).deleteCategory( anyString() );
        when( categoryRepository.existsById( anyString() ) ).thenReturn( false );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/categories/{id}", "42" );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( categoryController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isNotFound() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "Categoría no encontrada" ) );
    }

    /**
     * Method under test: {@link CategoryController#deleteCategory(String)}
     */
    @Test
    void test_DeleteCategory3() throws Exception {

        doNothing().when( categoryService ).deleteCategory( anyString() );
        when( categoryRepository.existsById( anyString() ) ).thenReturn( true );
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete( "/categories/{id}", "42" );
        deleteResult.characterEncoding( "Encoding" );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( categoryController )
                .build()
                .perform( deleteResult );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isNoContent() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "Borrado con éxito" ) );
    }

    /**
     * Method under test: {@link CategoryController#updateCategory(String, CategoryDto)}
     */
    @Test
    void test_UpdateCategory() throws Exception {

        doNothing().when( categoryService ).updateCategory( any(), anyString() );
        when( categoryRepository.existsById( anyString() ) ).thenReturn( true );

        CategoryDto categoryDto = getCategoryDto(  );
        String content = (new ObjectMapper()).writeValueAsString( categoryDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/categories/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( categoryController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "Categoria actualizada con éxito" ) );
    }

    /**
     * Method under test: {@link CategoryController#updateCategory(String, CategoryDto)}
     */
    @Test
    void test_UpdateCategory2() throws Exception {

        doNothing().when( categoryService ).updateCategory( any(), anyString() );
        when( categoryRepository.existsById( anyString() ) ).thenReturn( false );

        CategoryDto categoryDto = getCategoryDto(  );
        String content = (new ObjectMapper()).writeValueAsString( categoryDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/categories/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( categoryController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isNotFound() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "Categoría no encontrada" ) );
    }

    /**
     * Method under test: {@link CategoryController#getCategoryDetails(String)}
     */
    @Test
    void test_GetCategoryDetails() throws Exception {

        Category category = getCategory( );
        when( categoryService.getCategory( any() ) ).thenReturn( category );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/categories/{id}", "42" );
        MockMvcBuilders.standaloneSetup( categoryController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content()
                        .json(
                                "{\"id\":\"42\",\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"image\":\"Image\",\"timestamp\":10,\"deleted\":true}" ) );
    }

    /**
     * Method under test: {@link CategoryController#getCategoryDetails(String)}
     */
    @Test
    void test_GetCategoryDetails2() throws Exception {

        when( categoryService.getCategory( any() ) ).thenThrow( new RuntimeException() );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/categories/{id}", "42" );
        MockMvcBuilders.standaloneSetup( categoryController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isNotFound() )
                .andExpect( MockMvcResultMatchers.content()
                        .string( "" ) );
    }

    /**
     * Method under test: {@link CategoryController#getPagedUsers(int, String)}
     */
    @Test
    void test_GetPagedUsers() throws Exception {

        when( categoryService.getAllCategories( anyInt(), any() ) ).thenReturn( null );
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get( "/categories" ).param( "order", "foo" );
        MockHttpServletRequestBuilder requestBuilder = paramResult.param( "page", String.valueOf( 1 ) );
        MockMvcBuilders.standaloneSetup( categoryController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() );
    }
}

