package com.alkemy.ong.service.impl;

import com.alkemy.ong.utils.CustomMultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AmazonClientImpl.class})
@ExtendWith(SpringExtension.class)
class AmazonClientImplTest {

    @MockBean
    AmazonS3 amazonS3;

    private AmazonClientImpl amazonClientImpl;

    @BeforeEach
    void setUp() {

        amazonClientImpl = new AmazonClientImpl( "", "", "", "" );
        amazonClientImpl.setAmazonS3( amazonS3 );
    }

    /**
     * Method under test: {@link AmazonClientImpl#uploadFile(MultipartFile)}
     */
    @Test
    void testUploadFile() throws IOException {

        assertEquals( "", amazonClientImpl.uploadFile( new CustomMultipartFile() ) );
        assertEquals( "", amazonClientImpl
                .uploadFile( new MockMultipartFile( "/", new ByteArrayInputStream( "AAAAAAAA".getBytes( StandardCharsets.UTF_8 ) ) ) ) );
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
        verify( customMultipartFile, atLeast(2)).getOriginalFilename();
    }


}