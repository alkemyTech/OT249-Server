package com.alkemy.ong.controller;

import com.alkemy.ong.service.AmazonClient;
import com.alkemy.ong.utils.CustomMultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BucketController.class)
class BucketControllerTest {

    @MockBean
    AmazonClient amazonClient;

    BucketController bucketController;

    @BeforeEach
    void setUp() {

        bucketController = new BucketController( amazonClient );

    }

    /**
     * Method under test: {@link BucketController#uploadFile(MultipartFile)}
     */
    @Test
    @Disabled
    void testUploadFile() throws Exception {

        String content = getString( new MockMultipartFile( "Name", "Content".getBytes() ) );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/uploadFile" )
                .contentType( MediaType.MULTIPART_FORM_DATA_VALUE )
                .content( content );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( bucketController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isCreated() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "Miembro creado con éxito" ) );
    }

    private <T> String getString(T multipartFile) throws JsonProcessingException {

        return (new ObjectMapper()).writeValueAsString( multipartFile );
    }


    /**
     * Method under test: {@link BucketController#uploadFile(MultipartFile)}
     */
    @Test
    void testUploadFile2() {

        when( amazonClient.uploadFile( any() ) ).thenReturn( "Upload File" );
        assertEquals( "Upload File", bucketController.uploadFile( new CustomMultipartFile() ) );
        verify( amazonClient ).uploadFile( any() );
    }
}

