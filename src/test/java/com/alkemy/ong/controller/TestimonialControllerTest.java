package com.alkemy.ong.controller;

import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.exceptions.CustomExceptionController;
import com.alkemy.ong.exceptions.RecordException;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.TestimonialService;
import com.alkemy.ong.service.impl.TestimonialServiceImpl;
import com.alkemy.ong.utils.PageUtils;
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

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ContextConfiguration(classes = {TestimonialController.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( value = DisplayNameGenerator.ReplaceUnderscores.class)
class TestimonialControllerTest {

    private TestimonialController testimonialController;

    @MockBean
    private TestimonialService testimonialService;

    @MockBean
    private TestimonialRepository testimonialRepository;

    @SpyBean
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        testimonialController = new TestimonialController( testimonialService );
    }

    private static TestimonialDto getTestimonialDto(String content) {

        TestimonialDto testimonialDto = new TestimonialDto();
        testimonialDto.setContent( content );
        testimonialDto.setId( "42" );
        testimonialDto.setImage( "Image" );
        testimonialDto.setName( "Name" );
        testimonialDto.setSoftDelete( true );
        return testimonialDto;
    }
    /**
     * Method under test: {@link TestimonialController#createTestimony(TestimonialDto, BindingResult)}
     */
    @Test
    void CreateTestimony_cuando_es_validado_correctamente_deberia_devolver_la_entidad_creada() throws Exception {

        when( testimonialService.createTestimony( any(),  any() ) )
                .thenReturn( new TestimonialDto() );

        TestimonialDto testimonialDto = getTestimonialDto( "Content" );
        String content = (new ObjectMapper()).writeValueAsString( testimonialDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/testimonials" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( testimonialController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isCreated() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( content()
                        .json(
                                "{\"id\":null,\"name\":null,\"image\":null,\"content\":null,\"timestamp\":null,\"softDelete\":null}" ) );
    }


    /**
     * Method under test: {@link TestimonialController#createTestimony(TestimonialDto, BindingResult)}
     */
    @Test
    void CreateTestimony_cuando_se_pasa_no_valido_deberia_devolver_una_excepcion() throws Exception {

        testimonialController = new TestimonialController( new TestimonialServiceImpl(testimonialRepository, modelMapper) );
        TestimonialDto testimonialDto = new TestimonialDto();
        String content = (new ObjectMapper()).writeValueAsString( testimonialDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/testimonials" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( testimonialController )
                .setControllerAdvice( new CustomExceptionController() )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( content()
                        .json(
                                "{\"errorMessage\":\"Hay errores en lo enviado\",\"errorCode\":\"CLIENT_ERROR\",\"errorFields\":[{\"code\":\"NotBlank\",\"message\":\"must not be blank\",\"field\":\"name\"},{\"code\":\"NotBlank\",\"message\":\"must not be blank\",\"field\":\"content\"}]}\n" ) );
    }

    /**
     * Method under test: {@link TestimonialController#deleteTestimonial(String)}
     */
    @Test
    void DeleteTestimonial_cuando_borra_devuelve_un_numero_no_content() throws Exception {

        doNothing().when( testimonialService ).deleteTestimony(anyString() );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/testimonials/{id}", "42" );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( testimonialController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isNoContent() );
    }

    /**
     * Method under test: {@link TestimonialController#deleteTestimonial(String)}
     */
    @Test
    void DeleteTestimonial_cuando_no_encuentra_devolver_un_error() throws Exception {

        doThrow( new RecordException.RecordNotFoundException( "Testimony Not found" )  ).when( testimonialService ).deleteTestimony(anyString() );
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete( "/testimonials/{id}", "42" );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( testimonialController )
                .setControllerAdvice( new CustomExceptionController() )
                .build()
                .perform( deleteResult );
        actualPerformResult
                .andExpect( MockMvcResultMatchers.status().isNotFound() )
                .andExpect( jsonPath( "$.errors", is("Testimony Not found") ) );
    }

    /**
     * Method under test: {@link TestimonialController#getPagedController(int, String)}
     */
    @Test
    void GetPagedController_cuando_es_valido_deberia_devolver_el_paginado() throws Exception {

        when( testimonialService.getAllTestimonials( anyInt(),anyString() ) ).thenReturn( new PageDto<>( new ArrayList<>(), PageUtils.getPageable( 0,"asc" ),0 ) );
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get( "/testimonials/" ).param( "order", "foo" );
        MockHttpServletRequestBuilder requestBuilder = paramResult.param( "page", String.valueOf( 1 ) );
        MockMvcBuilders.standaloneSetup( testimonialController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( jsonPath( "$.content", Matchers.isA( ArrayList.class ) ))
                .andExpect( jsonPath( "$.content", hasSize( 0 ) ))
                .andExpect( jsonPath( "$.totalElements", is( 0 ) ));
    }
    /**
     * Method under test: {@link TestimonialController#getPagedController(int, String)}
     */
    @Test
    void GetPagedController_cuando_no_se_pasa_parametros_devuelve_la_pagina_cero_y_asc() throws Exception {

        //given
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass( String.class );
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass( Integer.class );
        //when
        when( testimonialService.getAllTestimonials( anyInt(),anyString() ) ).thenReturn( new PageDto<>( new ArrayList<>(), PageUtils.getPageable( 0,"asc" ),0 ) );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/testimonials/" );
        MockMvcBuilders.standaloneSetup( testimonialController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$.content", Matchers.isA( ArrayList.class ) ))
                .andExpect( jsonPath( "$.content", hasSize( 0 ) ))
                .andExpect( jsonPath( "$.totalElements", is( 0 ) ))
                .andExpect( jsonPath( "$.first", is( true ) ) )
                .andExpect( jsonPath( "$.number", is( 0 ) ) )        ;
        //then
        verify( testimonialService ).getAllTestimonials( integerArgumentCaptor.capture(), argumentCaptor.capture() );
        assertThat( integerArgumentCaptor.getValue() ).isEqualTo( 0 );
        assertThat( argumentCaptor.getValue() ).isEqualTo( "asc" );
    }
    /**
     * Method under test: {@link TestimonialController#updateTestimonial(String, TestimonialDto, BindingResult)}
     */
    @Test
    void UpdateTestimonial_cuando_es_valido_deberia_la_entidad() throws Exception {

        when( testimonialService.updateTestimony(anyString(), any(),  any() ) )
                .thenReturn( new TestimonialDto() );
        TestimonialDto testimonialDto = getTestimonialDto( "Not all who wander are lost");
        String content = (new ObjectMapper()).writeValueAsString( testimonialDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/testimonials/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( testimonialController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( content()
                        .json(
                                "{\"id\":null,\"name\":null,\"image\":null,\"content\":null,\"timestamp\":null,\"softDelete\":null}" ) );
    }

    /**
     * Method under test: {@link TestimonialController#updateTestimonial(String, TestimonialDto, BindingResult)}
     */
    @Test
    void UpdateTestimonial_cuando_no_es_valido_deberia_tirar_una_excepcion() throws Exception {

        testimonialController = new TestimonialController( new TestimonialServiceImpl(testimonialRepository, modelMapper) );
        TestimonialDto testimonialDto = new TestimonialDto();
        String content = (new ObjectMapper()).writeValueAsString( testimonialDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/testimonials/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( testimonialController )
                .alwaysDo( print() )
                .setControllerAdvice( new CustomExceptionController() )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( content()
                        .json(
                                "{\"errorMessage\":\"Hay errores en lo enviado\",\"errorCode\":\"CLIENT_ERROR\",\"errorFields\":[{\"field\":\"name\",\"message\":\"must not be blank\",\"code\":\"NotBlank\"},{\"field\":\"content\",\"message\":\"must not be blank\",\"code\":\"NotBlank\"}]}" ) );
    }
}

