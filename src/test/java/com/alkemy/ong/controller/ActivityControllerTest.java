package com.alkemy.ong.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.service.ActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ActivityController.class})
@ExtendWith(SpringExtension.class)
class ActivityControllerTest {

    @Autowired
    private ActivityController activityController;

    @MockBean
    private ActivityService activityService;

    /**
     * Method under test: {@link ActivityController#actualizarActividad(ActivityDto, String)}
     */
    @Test
    void test_ActualizarActividad() throws Exception {

        Activity activity = new Activity();
        activity.setContent( "Not all who wander are lost" );
        activity.setDeleted( true );
        activity.setId( "42" );
        activity.setImage( "Image" );
        activity.setName( "Name" );
        activity.setTimestamp( mock( Timestamp.class ) );
        Timestamp timestamp = mock( Timestamp.class );
        when( timestamp.getTime() ).thenReturn( 10L );

        Activity activity1 = new Activity();
        activity1.setContent( "Not all who wander are lost" );
        activity1.setDeleted( true );
        activity1.setId( "42" );
        activity1.setImage( "Image" );
        activity1.setName( "Name" );
        activity1.setTimestamp( timestamp );
        when( activityService.crearActivity( any() ) ).thenReturn( activity1 );
        when( activityService.findById( any() ) ).thenReturn( activity );

        ActivityDto activityDto = new ActivityDto();
        activityDto.setContent( "Not all who wander are lost" );
        activityDto.setImage( "Image" );
        activityDto.setName( "Name" );
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
}

