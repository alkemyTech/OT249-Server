package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.service.SlideService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {SlideController.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )

class SlideControllerTest {

    private SlideController slideController;

    @MockBean
    private SlideService slideService;

    @BeforeEach
    void setUp() {
        slideController = new SlideController( slideService );

    }

    /**
     * Method under test: {@link SlideController#getAll()}
     */
    @Test
    void testGetAll() throws Exception {

        when( slideService.getAll() ).thenReturn( new ArrayList<>() );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/slides" );
        MockMvcBuilders.standaloneSetup( slideController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().string( "[]" ) );
    }

    /**
     * Method under test: {@link SlideController#getAll()}
     */
    @Test
    void testGetAll2() throws Exception {

        when( slideService.getAll() ).thenReturn( new ArrayList<>() );
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get( "/slides" );
        getResult.characterEncoding( "Encoding" );
        MockMvcBuilders.standaloneSetup( slideController )
                .build()
                .perform( getResult )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().string( "[]" ) );
    }

    /**
     * Method under test: {@link SlideController#getById(String)}
     */
    @Test
    void testGetById() throws Exception {

        when( slideService.getById( anyString() ) ).thenReturn( new SlideResponseDto() );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/slides/{id}", "42" );
        MockMvcBuilders.standaloneSetup( slideController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"imageUrl\":null,\"text\":null,\"position\":null,\"publicOrganizationDto\":null}" ) );
    }

    /**
     * Method under test: {@link SlideController#getById(String)}
     */
    @Test
    void testGetById2() throws Exception {

        when( slideService.getAll() ).thenReturn( new ArrayList<>() );
        when( slideService.getById( anyString() ) ).thenReturn( new SlideResponseDto() );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/slides/{id}", "", "Uri Vars" );
        MockMvcBuilders.standaloneSetup( slideController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().string( "[]" ) );
    }

    /**
     * Method under test: {@link SlideController#delete(String)}
     */
    @Test
    void testDelete() throws Exception {

        doNothing().when( slideService ).delete( anyString() );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/slides/{id}", "42" );
        MockMvcBuilders.standaloneSetup( slideController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "Slide deleted" ) );
    }

    /**
     * Method under test: {@link SlideController#delete(String)}
     */
    @Test
    void testDelete2() throws Exception {

        doNothing().when( slideService ).delete( anyString() );
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete( "/slides/{id}", "42" );
        deleteResult.characterEncoding( "Encoding" );
        MockMvcBuilders.standaloneSetup( slideController )
                .build()
                .perform( deleteResult )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "Slide deleted" ) );
    }

    /**
     * Method under test: {@link SlideController#update(String, SlideRequestDto)}
     */
    @Test
    void testUpdate() throws Exception {

        when( slideService.update( anyString(), any() ) ).thenReturn( new SlideResponseDto() );

        SlideRequestDto slideRequestDto = new SlideRequestDto();
        slideRequestDto.setBase64Img( "Base64 Img" );
        slideRequestDto.setOrgId( "42" );
        slideRequestDto.setPosition( 1 );
        slideRequestDto.setText( "Text" );
        String content = (new ObjectMapper()).writeValueAsString( slideRequestDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/slides/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( slideController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"imageUrl\":null,\"text\":null,\"position\":null,\"publicOrganizationDto\":null}" ) );
    }

    /**
     * Method under test: {@link SlideController#update(String, SlideRequestDto)}
     */
    @Test
    void testUpdate2() throws Exception {

        when( slideService.update( anyString(), any() ) ).thenThrow( new Exception( "An error occurred" ) );

        SlideRequestDto slideRequestDto = new SlideRequestDto();
        slideRequestDto.setBase64Img( "Base64 Img" );
        slideRequestDto.setOrgId( "42" );
        slideRequestDto.setPosition( 1 );
        slideRequestDto.setText( "Text" );
        String content = (new ObjectMapper()).writeValueAsString( slideRequestDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/slides/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( slideController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isNotFound() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "An error occurred" ) );
    }

    /**
     * Method under test: {@link SlideController#save(SlideRequestDto)}
     */
    @Test
    void testSave() throws Exception {
        SlideRequestDto slideRequestDto = new SlideRequestDto();
        slideRequestDto.setBase64Img( "Base64 Img" );
        slideRequestDto.setOrgId( "42" );
        slideRequestDto.setPosition( 1 );
        slideRequestDto.setText( "Text" );

        when( slideService.save(any()) ).thenReturn( new SlideResponseDto() );

        String content = (new ObjectMapper()).writeValueAsString( slideRequestDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/slides" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( slideController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().json( "{\"id\":null,\"imageUrl\":null,\"text\":null,\"position\":null,\"publicOrganizationDto\":null}" ) );
    }
}

