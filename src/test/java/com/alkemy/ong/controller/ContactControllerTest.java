package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.service.ContactService;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ContactController.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )
class ContactControllerTest {

    private ContactController contactController;

    @MockBean
    private ContactService contactService;

    @BeforeEach
    void setUp() {

        contactController = new ContactController( contactService );

    }

    /**
     * Method under test: {@link ContactController#getContacts()}
     */
    @Test
    void testGetContacts() throws Exception {

        when( contactService.getAllContacts() ).thenReturn( new ArrayList<>() );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/contacts" );
        MockMvcBuilders.standaloneSetup( contactController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().string( "[]" ) );
    }

    /**
     * Method under test: {@link ContactController#getContacts()}
     */
    @Test
    void testGetContacts2() throws Exception {

        when( contactService.getAllContacts() ).thenReturn( new ArrayList<>() );
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get( "/contacts" );
        getResult.characterEncoding( "Encoding" );
        MockMvcBuilders.standaloneSetup( contactController )
                .build()
                .perform( getResult )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().json( "[]" ) );
    }

    /**
     * Method under test: {@link ContactController#saveContact(ContactDto)}
     */
    @Test
    void testSaveContact() throws Exception {


        ContactDto contactDto = new ContactDto();
        contactDto.setEmail( "jane.doe@example.org" );
        contactDto.setMessage( "Not all who wander are lost" );
        contactDto.setName( "Name" );
        contactDto.setPhone( "4105551212" );
        when( contactService.saveContact( any() ) ).thenReturn( contactDto );

        String content = (new ObjectMapper()).writeValueAsString( contactDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/contacts" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( contactController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().json( "{\"name\":\"Name\",\"phone\":\"4105551212\",\"email\":\"jane.doe@example.org\",\"message\":\"Not all who wander are lost\"}" ) );
    }
}

