package com.alkemy.ong.service.impl;

import com.alkemy.ong.utils.CustomMultipartFile;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AmazonClientImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )

class AmazonClientImplTest {

    @MockBean
    AmazonS3 amazonS3;

    private AmazonClientImpl amazonClientImpl;

    @BeforeEach
    void setUp() {

        amazonClientImpl = new AmazonClientImpl( "amazon", "bucket", "secretKeyAccess", "SecretKeyReal" );
        amazonClientImpl.setAmazonS3( amazonS3 );
    }

    /**
     * Method under test: {@link AmazonClientImpl#uploadFile(MultipartFile)}
     */
    @Test
    void testUploadFile() {

        CustomMultipartFile actual = new CustomMultipartFile( "AABB".getBytes(), "aa/bbb" );
        String actualFile = amazonClientImpl.uploadFile( actual );
        assertThat( actualFile ).isNotNull().isNotBlank().endsWith( ".bbb" );


        CustomMultipartFile actual1 = new CustomMultipartFile( "CC".getBytes(), "ccc/ddd" );

        String actualFile2 = amazonClientImpl
                .uploadFile( actual1 );
        assertThat( actualFile2 ).isNotNull().isNotBlank().endsWith( ".ddd" );

    }

    /**
     * Method under test: {@link AmazonClientImpl#uploadFile(MultipartFile)}
     */
    @Test
    void testUploadFile3() {

        String message = "Error1";
        MockMultipartFile mockMultipartFile =  new MockMultipartFile( "aaa/aaaa", "aaa", null, "aaa".getBytes() );
        when( amazonS3.putObject( any() ) ).thenThrow( new SdkClientException( message ) );
        String actualFile = amazonClientImpl
                .uploadFile( mockMultipartFile );
        assertThat( actualFile ).isNotNull();
        File file = Path.of( "aaa" ).toFile();
        if(file.exists())
            if(!file.delete())
                file.deleteOnExit();

    }

    /**
     * Method under test: {@link AmazonClientImpl#uploadFile(MultipartFile)}
     */
    @Test
    void testUploadFile2() throws IOException {

        amazonClientImpl.setAmazonS3( amazonS3 );
        CustomMultipartFile customMultipartFile = mock( CustomMultipartFile.class );
        when( customMultipartFile.getOriginalFilename() ).thenReturn( "foo.txt" );
        when( customMultipartFile.getBytes() ).thenReturn( "".getBytes() );
        String actual = amazonClientImpl.uploadFile( customMultipartFile );
        assertThat( actual ).isNotNull().isNotBlank().endsWith( "-foo.txt" );
        verify( customMultipartFile, atLeast( 2 ) ).getOriginalFilename();
    }


}