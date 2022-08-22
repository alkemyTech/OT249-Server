package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.service.ActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ActivityController.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )
class ActivityControllerTest {


    private ActivityController activityController;

    @BeforeEach
    void setUp() {
        activityController = new ActivityController( activityService );
    }

    @MockBean
    private ActivityService activityService;

    /**
     * Method under test: {@link ActivityController#actualizarActividad(ActivityDto, String)}
     */
    @Test
    void testActualizarActividad() throws Exception {
        Timestamp timestamp = mock( Timestamp.class );
        Activity activity = getActivity( timestamp );

        Activity activity1 = getActivity1( timestamp );
        when( activityService.crearActivity( any() ) ).thenReturn( activity1 );
        when( activityService.findById( any() ) ).thenReturn( activity );

        ActivityDto activityDto = getActivityDto();
        String content = (new ObjectMapper()).writeValueAsString( activityDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/activities/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( activityController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "application/json" ) )
                .andExpect( MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"42\",\"name\":\"Name\",\"content\":\"Not all who wander are lost\",\"image\":\"Image\",\"timestamp\":10,\"deleted"
                                        + "\":true}" ) );
    }

    private static ActivityDto getActivityDto() {

        ActivityDto activityDto = new ActivityDto();
        activityDto.setContent( "Not all who wander are lost" );
        activityDto.setImage( "Image" );
        activityDto.setName( "Name" );
        return activityDto;
    }

    private static Activity getActivity1(Timestamp timestamp) {

        Activity activity1 = new Activity();
        activity1.setContent( "Not all who wander are lost" );
        activity1.setDeleted( true );
        activity1.setId( "42" );
        activity1.setImage( "Image" );
        activity1.setName( "Name" );
        activity1.setTimestamp( timestamp );
        return activity1;
    }

    private static Activity getActivity(Timestamp timestamp) {

        Activity activity = getActivity1( timestamp );
        when( timestamp.getTime() ).thenReturn( 10L );
        return activity;
    }

    /**
     * Method under test: {@link ActivityController#actualizarActividad(ActivityDto, String)}
     */
    @Test
    void testActualizarActividad2() throws Exception {
        when( activityService.findById( any() ) ).thenReturn( null );

        ActivityDto activityDto = getActivityDto();
        String content = (new ObjectMapper()).writeValueAsString( activityDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/activities/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( activityController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isNotFound() );
    }



    /**
     * Method under test: {@link ActivityController#actualizarActividad(ActivityDto, String)}
     */
    @Test
    void testActualizarActividad3() throws Exception {
        Timestamp timestamp = mock( Timestamp.class );
        Activity activity = getActivity1( timestamp );
        when( timestamp.getTime() ).thenReturn( 10L );

        getActivity1( timestamp );
        when( activityService.crearActivity( any() ) ).thenThrow( new DataAccessException("error" ) {
            @Override
            public String getMessage() {

                return super.getMessage();
            }
        } );
        when( activityService.findById( any() ) ).thenReturn( activity );

        ActivityDto activityDto = getActivityDto();
        String content = (new ObjectMapper()).writeValueAsString( activityDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/activities/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( activityController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().is5xxServerError() )
               ;
    }
    /**
     * Method under test: {@link ActivityController#crearActividad(ActivityDto)}
     */
    @Test
    void testCrearActividad() throws Exception {

        Timestamp timestamp = mock( Timestamp.class );
        when( timestamp.getTime() ).thenReturn( 10L );

        Activity activity = getActivity1( timestamp );
        when( activityService.crearActivity( any() ) ).thenReturn( activity );

        ActivityDto activityDto = getActivityDto();
        String content = (new ObjectMapper()).writeValueAsString( activityDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/activities" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( activityController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "application/json" ) )
                .andExpect( MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"42\",\"name\":\"Name\",\"content\":\"Not all who wander are lost\",\"image\":\"Image\",\"timestamp\":10,\"deleted"
                                        + "\":true}" ) );
    }
}

