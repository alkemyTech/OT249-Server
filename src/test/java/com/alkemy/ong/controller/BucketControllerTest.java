package com.alkemy.ong.controller;

import com.alkemy.ong.service.impl.AmazonClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BucketController.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )
class BucketControllerTest {

    @MockBean
    AmazonClientImpl amazonClientImpl;

    BucketController bucketController;

    @BeforeEach
    void setUp() {

        bucketController = new BucketController( amazonClientImpl );

    }

    /**
     * Method under test: {@link BucketController#uploadFile(MultipartFile)}
     */
    @Test

    void testUploadFile() throws Exception {

        when( amazonClientImpl.uploadFile( any() ) ).thenReturn( "Result" );

        MockMultipartFile mockMultipartFile = new MockMultipartFile( "file","file.text",
                MediaType.MULTIPART_FORM_DATA_VALUE, "test data".getBytes() );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart( "/uploadFile" ).file( mockMultipartFile )
                .contentType( MediaType.MULTIPART_FORM_DATA_VALUE );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( bucketController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "Result" ) );
    }

    /**
     * Method under test: {@link BucketController#uploadFile(MultipartFile)}
     */
    @Test
    void testUploadFile2() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("aaaa", "".getBytes());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart( "/uploadFile" ).file( mockMultipartFile )
                .contentType( MediaType.MULTIPART_FORM_DATA_VALUE );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( bucketController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isBadRequest() );
    }

}

