package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CreateNewsDto;
import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.exceptions.CustomExceptionController;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.security.CustomExceptionHandler;
import com.alkemy.ong.service.NewsService;
import com.alkemy.ong.service.impl.NewsServiceImpl;
import com.alkemy.ong.utils.PageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {NewController.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class NewControllerTest {

    private NewController newController;

    @MockBean
    private CategoryRepository categoryRepository;

    @SpyBean
    private ModelMapper modelMapper;

    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private NewsService newsService;

    @BeforeEach
    public void setUp() {

        newController = new NewController( newsService, categoryRepository );
    }

    private static NewDTO getNewDTO(String content, CategoryDto categoryDto) {

        NewDTO newDTO = new NewDTO();
        newDTO.setCategory( categoryDto );
        newDTO.setContent( content );
        newDTO.setId( "42" );
        newDTO.setImage( "Image" );
        newDTO.setName( "Name" );
        newDTO.setSoftDelete( false );
        newDTO.setTimestamp( null );
        return newDTO;
    }


    private static CreateNewsDto getDto() {

        CreateNewsDto newsDto = new CreateNewsDto();
        newsDto.setContent( "--" );
        newsDto.setName( "6" );
        newsDto.setIdCategory( "54" );
        newsDto.setImage( "aaaa" );
        return newsDto;
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

    private static <T> String getAsString(T t) throws JsonProcessingException {

        return (new ObjectMapper()).writeValueAsString( t );
    }


    private static News getNews(Category category) {

        News news = new News();
        news.setCategory( category );
        news.setContent( "Not all who wander are lost" );
        news.setId( "42" );
        news.setImage( "Image" );
        news.setName( "Name" );
        news.setSoftDelete( true );
        return news;
    }

    /**
     * Methfuzzunder test: {@link NewController#createNews(CreateNewsDto)}
     */
    @Test
    void createNews_cuando_no_hay_problemas_deberia_devolver_created() throws Exception {

        CreateNewsDto createNewsDto = getDto();
        when( categoryRepository.findById( anyString() ) )
                .thenReturn( Optional.of( new Category() ) );
        doNothing()
                .when( newsService )
                .createNews( any( News.class ) );
        String content = getAsString( createNewsDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post( "/news" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        ResultActions actualPerformResult = MockMvcBuilders
                .standaloneSetup( newController )
                .build()
                .perform( requestBuilder );
        actualPerformResult
                .andExpect( status().isCreated() )
                .andExpect( content().string( "Novedad creada correctamente" ) );
    }

    /**
     * Method under test: {@link NewController#createNews(CreateNewsDto)}
     */
    @Test
    void createNews_cuando_se_pasa_un_valor_nulo_deberia_devolver_un_error_de_validacion() throws Exception {

        when( categoryRepository.findById( anyString() ) ).thenReturn( Optional.of( new Category()
        ) );
        CreateNewsDto createNewsDto = new CreateNewsDto();
        String content = getAsString( createNewsDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/news" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( newController )
                .build()
                .perform( requestBuilder );
        actualPerformResult
                .andExpect( status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.content().string( "" ) );
    }

    /**
     * Method under test: {@link NewController#deleteNews(String)}
     */
    @Test
    void deleteNews_cuando_todo_no_hay_problemas_deberia_devolver_ok() throws Exception {

        Category category = getCategory();

        News news = getNews( category );
        news.setTimestamp( LocalDateTime.of( 1, 1, 1, 1, 1 ) );
        when( newsService.deleteNews( any() ) ).thenReturn( true );
        when( newsService.findNewsById( any() ) ).thenReturn( news );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/news/news/{id}", "42" );
        MockMvcBuilders.standaloneSetup( newController )
                .build()
                .perform( requestBuilder )
                .andExpect( status().isOk() );
    }

    /**
     * Method under test: {@link NewController#deleteNews(String)}
     */
    @Test
    void deleteNews_cuando_todo_no_hay_problemas_deberia_devolver_un_error_bad_request() throws Exception {

        when( newsService.deleteNews( anyString() ) ).thenReturn( false );
        when( newsService.findNewsById( anyString() ) ).thenReturn( null );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/news/news/{id}", "42" );
        MockMvcBuilders.standaloneSetup( newController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isNotFound() );
    }


    /**
     * Method under test: {@link NewController#deleteNews(String)}
     */
    @Test
    void deleteNews_cuando_no_se_encuentra_deberia_devolver_un_error_not_found() throws Exception {

        when( newsService.deleteNews( any() ) ).thenReturn( true );
        when( newsService.findNewsById( any() ) ).thenReturn( null );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/news/news/{id}", "42" );
        MockMvcBuilders.standaloneSetup( newController )
                .build()
                .perform( requestBuilder )
                .andExpect( status().isNotFound() );
    }

    /**
     * Method under test: {@link NewController#getPagedController(int, String)}
     */
    @Test
    void GetPagedController_cuando_se_pasa_correctamente_parametros_devuelve_un_ok() throws Exception {

        when( newsService.getAllNews( anyInt(), anyString() ) ).thenReturn( new PageDto<>( new ArrayList<>(), PageUtils.getPageable( 0, "asc" ),0 ) );
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get( "/news/" ).param( "order", "foo" );
        MockHttpServletRequestBuilder requestBuilder = paramResult.param( "page", String.valueOf( 1 ) );
        MockMvcBuilders.standaloneSetup( newController )
                .build()
                .perform( requestBuilder )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.status().isOk() );
    }

    /**
     * Method under test: {@link NewController#getPagedController(int, String)}
     */
    @Test
    void GetPagedController_cuando_no_se_pasa_parametros_devuelve_la_pagina_cero_y_asc() throws Exception {

        //given
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass( String.class );
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass( Integer.class );

        //when
        when( newsService.getAllNews( anyInt(), anyString() ) ).thenReturn( new PageDto<>( new ArrayList<>(), PageUtils.getPageable( 0, "asc" ), 0 ) );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/news/" );
        MockMvcBuilders.standaloneSetup( newController )
                .build()
                .perform( requestBuilder )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( jsonPath( "$.first", is( true ) ) )
                .andExpect( jsonPath( "$.number", is( 0 ) ) )
        ;
        //then
        verify( newsService ).getAllNews( integerArgumentCaptor.capture(), argumentCaptor.capture() );
        assertThat( integerArgumentCaptor.getValue() ).isEqualTo( 0 );
        assertThat( argumentCaptor.getValue() ).isEqualTo( "asc" );
    }

    /**
     * Method under test: {@link NewController#updateNews(String, NewDTO, BindingResult)}
     */
    @Test
    void UpdateNews_cuando_se_pasan_valores_nulos_deberia_tirar_una_excepcion_de_bind_result_exception() throws Exception {

        newsService = new NewsServiceImpl( newsRepository, categoryRepository, modelMapper );
        NewController newController = new NewController( newsService, categoryRepository );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/news/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( getAsString( new NewDTO() ) );
        MockMvcBuilders.standaloneSetup( newController )
                .setControllerAdvice( new CustomExceptionController() )
                .build()
                .perform( requestBuilder )
                .andExpect( status().isBadRequest() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$.errorMessage", is( "Hay errores en lo enviado" ) ) )
                .andExpect( jsonPath( "$.errorFields", hasSize( 3 ) ) )
                .andExpect( jsonPath( "$.errorFields", Matchers.isA( ArrayList.class ) ) )
                .andExpect( jsonPath( "$.errorCode", is( "CLIENT_ERROR" ) ) );
    }


    /**
     * Method under test: {@link NewController#updateNews(String, NewDTO, BindingResult)}
     */
    @Test
    void UpdateNews_cuando_todo_es_correct_deberia_devolver_la_entidad_actualizada() throws Exception {


        CategoryDto categoryDto = new CategoryDto();
        NewDTO newDTO = getNewDTO( "aa", categoryDto );
        when( newsService.updateNews( any(), any(), any() ) ).thenReturn( newDTO );
        String content = getAsString( newDTO );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/news/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( newController )
                .build()
                .perform( requestBuilder )
                .andExpect( status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$.id", is( newDTO.getId() ) ) )
                .andExpect( jsonPath( "$.name", is( newDTO.getName() ) ) )
                .andExpect( jsonPath( "$.image", is( newDTO.getImage() ) ) )
                .andExpect( jsonPath( "$.timestamp", is( newDTO.getTimestamp() ) ) )
                .andExpect( jsonPath( "$.category.name", is( categoryDto.getName() ) ) )
                .andExpect( jsonPath( "$.softDelete", is( newDTO.isSoftDelete() ) ) )
                .andExpect( jsonPath( "$.content", Matchers.is( newDTO.getContent() ) ) )

                .andExpect( MockMvcResultMatchers.content()
                        .json( getAsString( newDTO ) ) );
    }


    /**
     * Method under test: {@link NewController#NewDetail(String)}
     */
    @Test
    void NewDetail_cuando_no_se_encuentra_deberia_devolver_not_found() throws Exception {

        when( newsService.getNews( any() ) ).thenThrow( new Exception( "New not found" ) );

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/news/{id}", "42" );
        MockMvcBuilders.standaloneSetup( newController ).setControllerAdvice( new CustomExceptionHandler() )
                .build()
                .perform( requestBuilder )
                .andExpect( status().isNotFound() )
                .andExpect( content().string( "" ) );
    }

    /**
     * Method under test: {@link NewController#NewDetail(String)}
     */
    @Test
    void NewDetail_cuando_se_pasa_un_id_y_lo_encuentra_deberia_devolver_la_entidad() throws Exception {

        CategoryDto categoryDto = new CategoryDto();
        NewDTO newDTO = getNewDTO( "AACCC", categoryDto );
        when( newsService.getNews( any() ) ).thenReturn( newDTO );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/news/{id}", "42" );
        MockMvcBuilders.standaloneSetup( newController )
                .build()
                .perform( requestBuilder )
                .andExpect( status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$.id", is( newDTO.getId() ) ) )
                .andExpect( jsonPath( "$.name", is( newDTO.getName() ) ) )
                .andExpect( jsonPath( "$.image", is( newDTO.getImage() ) ) )
                .andExpect( jsonPath( "$.timestamp", is( newDTO.getTimestamp() ) ) )
                .andExpect( jsonPath( "$.category.name", is( categoryDto.getName() ) ) )
                .andExpect( jsonPath( "$.softDelete", is( newDTO.isSoftDelete() ) ) )
                .andExpect( jsonPath( "$.content", Matchers.is( newDTO.getContent() ) ) )
                .andExpect( MockMvcResultMatchers.content()
                        .json(
                                "{\"id\":\"42\",\"name\":\"Name\",\"content\":\"AACCC\",\"image\":\"Image\",\"timestamp\":null,\"category\":{},\"softDelete"
                                        + "\":false}" ) );
    }

}

