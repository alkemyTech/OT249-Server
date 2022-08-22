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

        amazonClientImpl = new AmazonClientImpl( "endpointUrl", "bucketName", "AccessKey", "SecretKeyRealSuperSecret" );
        amazonClientImpl.setAmazonS3( amazonS3 );
    }

    /**
     * Method under test: {@link AmazonClientImpl#uploadFile(MultipartFile)}
     */
    @Test
    void UploadFile_cuando_tira_una_excepcion_devuelve_el_nombre_de_archivo() {

        String message = "Error1";
        MockMultipartFile actualMultipartFile =  new MockMultipartFile( "aaa/aaaa", "aaa.txt", null, "aaa".getBytes() );
        when( amazonS3.putObject( any() ) ).thenThrow( new SdkClientException( message ) );

        String actualFile = amazonClientImpl.uploadFile( actualMultipartFile );
        assertThat( actualFile ).isNotNull().isNotBlank().endsWith( "aaa.txt" );
        File file1 = Path.of( "aaa.txt" ).toFile();
        assertThat( file1 ).exists();
        if(file1.exists())
            if(!file1.delete())
                file1.deleteOnExit();


        MockMultipartFile actualMultipartFile1 =  new MockMultipartFile( "aaa/aaaa", "bbb.txt", null, "aaa".getBytes() );

        String actualFile2 = amazonClientImpl
                .uploadFile( actualMultipartFile1 );
        assertThat( actualFile2 ).isNotNull().isNotBlank().endsWith( "bbb.txt" );
        File file = Path.of( "bbb.txt" ).toFile();
        assertThat( file ).exists();
        if(file.exists())
            if(!file.delete())
                file.deleteOnExit();

    }

    /**
     * Method under test: {@link AmazonClientImpl#uploadFile(MultipartFile)}
     */
    @Test
    void UploadFile_cuando_no_tira_una_excepcion_devuelve_el_nombre_de_archivo_y_el_archivo_existe() {

        String message = "Error1";
        MockMultipartFile mockMultipartFile =  new MockMultipartFile( "aaa/aaaa", "ccc.txt", null, "aaa".getBytes() );
        when( amazonS3.putObject( any() ) ).thenThrow( new SdkClientException( message ) );
        String actualFile = amazonClientImpl.uploadFile( mockMultipartFile );
        assertThat( actualFile ).isNotNull();
        File file = Path.of( "ccc.txt" ).toFile();
        assertThat( file ).exists();
        if(file.exists())
            if(!file.delete())
                file.deleteOnExit();

    }

    /**
     * Method under test: {@link AmazonClientImpl#uploadFile(MultipartFile)}
     */
    @Test
    void UploadFile_cuando_no_tira_una_excepcion_devuelve_el_nombre_de_archivo_y_el_archivo_no_existe() throws IOException {

        CustomMultipartFile customMultipartFile = mock( CustomMultipartFile.class );
        when( customMultipartFile.getOriginalFilename() ).thenReturn( "foo.txt" );
        when( customMultipartFile.getBytes() ).thenReturn( "".getBytes() );
        String actual = amazonClientImpl.uploadFile( customMultipartFile );
        assertThat( actual ).isNotNull().isNotBlank().endsWith( "-foo.txt" );
        verify( customMultipartFile, atLeast( 2 ) ).getOriginalFilename();
        File file = Path.of( "foo.txt" ).toFile();
        assertThat( file ).doesNotExist();
        if(file.exists())
            if(!file.delete())
                file.deleteOnExit();

    }


}