package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.PublicOrganizationDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.exceptions.RecordException;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.AmazonClient;
import com.alkemy.ong.service.OrganizationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {SlideServiceImpl.class})
@ExtendWith(SpringExtension.class)
class SlideServiceImplTest {

    @MockBean
    private AmazonClient amazonClient;

    @SpyBean
    private ModelMapper modelMapper;

    @MockBean
    private OrganizationRepository organizationRepository;

    @MockBean
    private OrganizationService organizationService;

    @MockBean
    private SlideRepository slideRepository;

    @Autowired
    private SlideServiceImpl slideServiceImpl;

    /**
     * Method under test: {@link SlideServiceImpl#getAll()}
     */
    @Test
    void testGetAll() {

        when( slideRepository.findAll() ).thenReturn( new ArrayList<>() );
        assertTrue( slideServiceImpl.getAll().isEmpty() );
        verify( slideRepository ).findAll();
    }

    /**
     * Method under test: {@link SlideServiceImpl#getAll()}
     */
    @Test
    void testGetAll2() {

        when( modelMapper.map( any(), any() ) ).thenReturn( new SlideDto() );

        Organization organization = new Organization();
        organization.setAboutUsText( "About Us Text" );
        organization.setAddress( "42 Main St" );
        organization.setDeleted( true );
        organization.setEmail( "jane.doe@example.org" );
        organization.setId( "42" );
        organization.setImage( "Image" );
        organization.setName( "Name" );
        organization.setPhone( "4105551212" );
        organization.setTimestamp( mock( Timestamp.class ) );
        organization.setUrlFacebook( "https://example.org/example" );
        organization.setUrlInstagram( "https://example.org/example" );
        organization.setUrlLinkedin( "https://example.org/example" );
        organization.setWelcomeText( "Welcome Text" );

        Slide slide = new Slide();
        slide.setId( "42" );
        slide.setImageUrl( "https://example.org/example" );
        slide.setOrganization( organization );
        slide.setPosition( 1 );
        slide.setText( "Text" );

        ArrayList<Slide> slideList = new ArrayList<>();
        slideList.add( slide );
        when( slideRepository.findAll() ).thenReturn( slideList );
        assertEquals( 1, slideServiceImpl.getAll().size() );
        verify( modelMapper ).map( any(), any() );
        verify( slideRepository ).findAll();
    }

    /**
     * Method under test: {@link SlideServiceImpl#getAll()}
     */
    @Test
    void testGetAll3() {

        when( modelMapper.map( any(), any() ) ).thenReturn( new SlideDto() );

        Organization organization = new Organization();
        organization.setAboutUsText( "About Us Text" );
        organization.setAddress( "42 Main St" );
        organization.setDeleted( true );
        organization.setEmail( "jane.doe@example.org" );
        organization.setId( "42" );
        organization.setImage( "Image" );
        organization.setName( "Name" );
        organization.setPhone( "4105551212" );
        organization.setTimestamp( mock( Timestamp.class ) );
        organization.setUrlFacebook( "https://example.org/example" );
        organization.setUrlInstagram( "https://example.org/example" );
        organization.setUrlLinkedin( "https://example.org/example" );
        organization.setWelcomeText( "Welcome Text" );

        Slide slide = new Slide();
        slide.setId( "42" );
        slide.setImageUrl( "https://example.org/example" );
        slide.setOrganization( organization );
        slide.setPosition( 1 );
        slide.setText( "Text" );

        Organization organization1 = new Organization();
        organization1.setAboutUsText( "About Us Text" );
        organization1.setAddress( "42 Main St" );
        organization1.setDeleted( true );
        organization1.setEmail( "jane.doe@example.org" );
        organization1.setId( "42" );
        organization1.setImage( "Image" );
        organization1.setName( "Name" );
        organization1.setPhone( "4105551212" );
        organization1.setTimestamp( mock( Timestamp.class ) );
        organization1.setUrlFacebook( "https://example.org/example" );
        organization1.setUrlInstagram( "https://example.org/example" );
        organization1.setUrlLinkedin( "https://example.org/example" );
        organization1.setWelcomeText( "Welcome Text" );

        Slide slide1 = new Slide();
        slide1.setId( "42" );
        slide1.setImageUrl( "https://example.org/example" );
        slide1.setOrganization( organization1 );
        slide1.setPosition( 1 );
        slide1.setText( "Text" );

        ArrayList<Slide> slideList = new ArrayList<>();
        slideList.add( slide1 );
        slideList.add( slide );
        when( slideRepository.findAll() ).thenReturn( slideList );
        assertEquals( 2, slideServiceImpl.getAll().size() );
        verify( modelMapper, atLeast( 1 ) ).map( any(), any() );
        verify( slideRepository ).findAll();
    }

    /**
     * Method under test: {@link SlideServiceImpl#getAll()}
     */
    @Test
    void testGetAll4() {

        when( modelMapper.map( any(), any() ) )
                .thenThrow( new RecordException.RecordNotFoundException( "An error occurred" ) );

        Organization organization = new Organization();
        organization.setAboutUsText( "About Us Text" );
        organization.setAddress( "42 Main St" );
        organization.setDeleted( true );
        organization.setEmail( "jane.doe@example.org" );
        organization.setId( "42" );
        organization.setImage( "Image" );
        organization.setName( "Name" );
        organization.setPhone( "4105551212" );
        organization.setTimestamp( mock( Timestamp.class ) );
        organization.setUrlFacebook( "https://example.org/example" );
        organization.setUrlInstagram( "https://example.org/example" );
        organization.setUrlLinkedin( "https://example.org/example" );
        organization.setWelcomeText( "Welcome Text" );

        Slide slide = new Slide();
        slide.setId( "42" );
        slide.setImageUrl( "https://example.org/example" );
        slide.setOrganization( organization );
        slide.setPosition( 1 );
        slide.setText( "Text" );

        ArrayList<Slide> slideList = new ArrayList<>();
        slideList.add( slide );
        when( slideRepository.findAll() ).thenReturn( slideList );
        assertThrows( RecordException.RecordNotFoundException.class, () -> slideServiceImpl.getAll() );
        verify( modelMapper ).map( any(), any() );
        verify( slideRepository ).findAll();
    }

    /**
     * Method under test: {@link SlideServiceImpl#getById(String)}
     */
    @Test
    void testGetById() {

        when( slideRepository.getById( anyString() ) )
                .thenThrow( new RecordException.RecordNotFoundException( "An error occurred" ) );
        assertThrows( RecordException.RecordNotFoundException.class, () -> slideServiceImpl.getById( "42" ) );
        verifyNoInteractions( modelMapper );
        verify( slideRepository ).getById( any() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#delete(String)}
     */
    @Test
    void testDelete() {

        Organization organization = new Organization();
        organization.setAboutUsText( "About Us Text" );
        organization.setAddress( "42 Main St" );
        organization.setDeleted( true );
        organization.setEmail( "jane.doe@example.org" );
        organization.setId( "42" );
        organization.setImage( "Image" );
        organization.setName( "Name" );
        organization.setPhone( "4105551212" );
        organization.setTimestamp( mock( Timestamp.class ) );
        organization.setUrlFacebook( "https://example.org/example" );
        organization.setUrlInstagram( "https://example.org/example" );
        organization.setUrlLinkedin( "https://example.org/example" );
        organization.setWelcomeText( "Welcome Text" );

        Slide slide = new Slide();
        slide.setId( "42" );
        slide.setImageUrl( "https://example.org/example" );
        slide.setOrganization( organization );
        slide.setPosition( 1 );
        slide.setText( "Text" );
        Optional<Slide> ofResult = Optional.of( slide );
        doNothing().when( slideRepository ).delete( any() );
        when( slideRepository.findById( anyString() ) ).thenReturn( ofResult );
        slideServiceImpl.delete( "42" );
        verify( slideRepository ).findById( anyString() );
        verify( slideRepository ).delete( any() );
        assertTrue( slideServiceImpl.getAll().isEmpty() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#delete(String)}
     */
    @Test
    void testDelete2() {

        Organization organization = new Organization();
        organization.setAboutUsText( "About Us Text" );
        organization.setAddress( "42 Main St" );
        organization.setDeleted( true );
        organization.setEmail( "jane.doe@example.org" );
        organization.setId( "42" );
        organization.setImage( "Image" );
        organization.setName( "Name" );
        organization.setPhone( "4105551212" );
        organization.setTimestamp( mock( Timestamp.class ) );
        organization.setUrlFacebook( "https://example.org/example" );
        organization.setUrlInstagram( "https://example.org/example" );
        organization.setUrlLinkedin( "https://example.org/example" );
        organization.setWelcomeText( "Welcome Text" );

        Slide slide = new Slide();
        slide.setId( "42" );
        slide.setImageUrl( "https://example.org/example" );
        slide.setOrganization( organization );
        slide.setPosition( 1 );
        slide.setText( "Text" );
        Optional<Slide> ofResult = Optional.of( slide );
        doThrow( new RecordException.RecordNotFoundException( "An error occurred" ) ).when( slideRepository )
                .delete( any() );
        when( slideRepository.findById( anyString() ) ).thenReturn( ofResult );
        assertThrows( RecordException.RecordNotFoundException.class, () -> slideServiceImpl.delete( "42" ) );
        verify( slideRepository ).findById( anyString() );
        verify( slideRepository ).delete( any() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#delete(String)}
     */
    @Test
    void testDelete3() {

        doNothing().when( slideRepository ).delete( any() );
        when( slideRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        assertThrows( RecordException.RecordNotFoundException.class, () -> slideServiceImpl.delete( "42" ) );
        verify( slideRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#update(String, SlideRequestDto)}
     */
    @Test
    void testUpdate()  {
        Organization organization = new Organization();
        organization.setAboutUsText( "About Us Text" );
        organization.setAddress( "42 Main St" );
        organization.setDeleted( true );
        organization.setEmail( "jane.doe@example.org" );
        organization.setId( "42" );
        organization.setImage( "Image" );
        organization.setName( "Name" );
        organization.setPhone( "4105551212" );
        organization.setTimestamp( mock( Timestamp.class ) );
        organization.setUrlFacebook( "https://example.org/example" );
        organization.setUrlInstagram( "https://example.org/example" );
        organization.setUrlLinkedin( "https://example.org/example" );
        organization.setWelcomeText( "Welcome Text" );

        Slide slide = new Slide();
        slide.setId( "42" );
        slide.setImageUrl( "https://example.org/example" );
        slide.setOrganization( organization );
        slide.setPosition( 1 );
        slide.setText( "Text" );
        Optional<Slide> ofResult = Optional.of( slide );
        when( slideRepository.save( any() ) )
                .thenThrow( new RecordException.RecordNotFoundException( "An error occurred" ) );
        when( slideRepository.findById( anyString() ) ).thenReturn( ofResult );
        assertThrows( RecordException.RecordNotFoundException.class,
                () -> slideServiceImpl.update( "42", new SlideRequestDto() ) );
        verify( slideRepository ).save( any() );
        verify( slideRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#update(String, SlideRequestDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdate2() throws Exception {
        // TODO: Complete this test.

        Organization organization = new Organization();
        organization.setAboutUsText( "About Us Text" );
        organization.setAddress( "42 Main St" );
        organization.setDeleted( true );
        organization.setEmail( "jane.doe@example.org" );
        organization.setId( "42" );
        organization.setImage( "Image" );
        organization.setName( "Name" );
        organization.setPhone( "4105551212" );
        organization.setTimestamp( mock( Timestamp.class ) );
        organization.setUrlFacebook( "https://example.org/example" );
        organization.setUrlInstagram( "https://example.org/example" );
        organization.setUrlLinkedin( "https://example.org/example" );
        organization.setWelcomeText( "Welcome Text" );

        Slide slide = new Slide();
        slide.setId( "42" );
        slide.setImageUrl( "https://example.org/example" );
        slide.setOrganization( organization );
        slide.setPosition( 1 );
        slide.setText( "Text" );
        Optional<Slide> ofResult = Optional.of( slide );

        Organization organization1 = new Organization();
        organization1.setAboutUsText( "About Us Text" );
        organization1.setAddress( "42 Main St" );
        organization1.setDeleted( true );
        organization1.setEmail( "jane.doe@example.org" );
        organization1.setId( "42" );
        organization1.setImage( "Image" );
        organization1.setName( "Name" );
        organization1.setPhone( "4105551212" );
        organization1.setTimestamp( mock( Timestamp.class ) );
        organization1.setUrlFacebook( "https://example.org/example" );
        organization1.setUrlInstagram( "https://example.org/example" );
        organization1.setUrlLinkedin( "https://example.org/example" );
        organization1.setWelcomeText( "Welcome Text" );

        Slide slide1 = new Slide();
        slide1.setId( "42" );
        slide1.setImageUrl( "https://example.org/example" );
        slide1.setOrganization( organization1 );
        slide1.setPosition( 1 );
        slide1.setText( "Text" );
        when( slideRepository.save( any() ) ).thenReturn( slide1 );
        when( slideRepository.findById( anyString() ) ).thenReturn( ofResult );
        slideServiceImpl.update( "42", new SlideRequestDto() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#slideForOng(String)}
     */
    @Test
    void testSlideForOng()  {

        PublicOrganizationDto organizationDto = new PublicOrganizationDto( "42", "Name", "Image", "4105551212", "42 Main St",
                "https://example.org/example", "https://example.org/example", "https://example.org/example" );
        Organization organization = new Organization();
        organization.setAboutUsText( "About Us Text" );
        organization.setAddress( "42 Main St" );
        organization.setDeleted( true );
        organization.setEmail( "jane.doe@example.org" );
        organization.setId( "42" );
        organization.setImage( "Image" );
        organization.setName( "Name" );
        organization.setPhone( "4105551212" );
        organization.setTimestamp( mock( Timestamp.class ) );
        organization.setUrlFacebook( "https://example.org/example" );
        organization.setUrlInstagram( "https://example.org/example" );
        organization.setUrlLinkedin( "https://example.org/example" );
        organization.setWelcomeText( "Welcome Text" );
        Optional<Organization> ofResult = Optional.of( organization );
        when( organizationRepository.findById( anyString() ) ).thenReturn( ofResult );
        when( slideRepository.findByOrganization_idLikeOrderByPositionDesc( anyString() ) ).thenReturn( new ArrayList<>() );
        assertThrows( Exception.class, () -> slideServiceImpl.slideForOng( "42" ) );
        verify( modelMapper ).map(  any(),  any() );
        verify( organizationRepository ).findById( anyString() );
        verify( slideRepository ).findByOrganization_idLikeOrderByPositionDesc( anyString() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#slideForOng(String)}
     */
    @Test
    void testSlideForOng2()  {

        PublicOrganizationDto organizationDto = new PublicOrganizationDto( "42", "Name", "Image", "4105551212", "42 Main St",
                "https://example.org/example", "https://example.org/example", "https://example.org/example" );
        Organization organization = new Organization();
        organization.setAboutUsText( "About Us Text" );
        organization.setAddress( "42 Main St" );
        organization.setDeleted( true );
        organization.setEmail( "jane.doe@example.org" );
        organization.setId( "42" );
        organization.setImage( "Image" );
        organization.setName( "Name" );
        organization.setPhone( "4105551212" );
        organization.setTimestamp( mock( Timestamp.class ) );
        organization.setUrlFacebook( "https://example.org/example" );
        organization.setUrlInstagram( "https://example.org/example" );
        organization.setUrlLinkedin( "https://example.org/example" );
        organization.setWelcomeText( "Welcome Text" );
        Optional<Organization> ofResult = Optional.of( organization );
        when( organizationRepository.findById( anyString() ) ).thenReturn( ofResult );
        when( slideRepository.findByOrganization_idLikeOrderByPositionDesc( anyString() ) )
                .thenThrow( new RecordException.RecordNotFoundException( "An error occurred" ) );
        assertThrows( RecordException.RecordNotFoundException.class, () -> slideServiceImpl.slideForOng( "42" ) );
        verify( modelMapper ).map(  any(),  any() );
        verify( organizationRepository ).findById( anyString() );
        verify( slideRepository ).findByOrganization_idLikeOrderByPositionDesc( anyString() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#slideForOng(String)}
     */
    @Test
    void testSlideForOng3()  {

        when( modelMapper.map( any(), any() ) ).thenReturn( "Map" );
        PublicOrganizationDto publicOrganizationDto = new PublicOrganizationDto( "42", "Name", "Image", "4105551212", "42 Main St",
                "https://example.org/example", "https://example.org/example", "https://example.org/example" );
        when( organizationRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        when( slideRepository.findByOrganization_idLikeOrderByPositionDesc( anyString() ) ).thenReturn( new ArrayList<>() );
        assertThrows( Exception.class, () -> slideServiceImpl.slideForOng( "42" ) );
        verify( modelMapper ).map(  any(),  any() );
        verify( organizationRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#save(SlideRequestDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSave() {
        // TODO: Complete this test.

        slideServiceImpl.save( new SlideRequestDto() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#save(SlideRequestDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSave2() {
        // TODO: Complete this test.

        SlideRequestDto slideRequestDto = new SlideRequestDto();
        slideRequestDto.setBase64Img( "," );
        slideServiceImpl.save( slideRequestDto );
    }

    /**
     * Method under test: {@link SlideServiceImpl#save(SlideRequestDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSave3() {
        // TODO: Complete this test.

        slideServiceImpl.save( null );
    }

    /**
     * Method under test: {@link SlideServiceImpl#save(SlideRequestDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSave4() {
        // TODO: Complete this test.

        SlideRequestDto slideRequestDto = mock( SlideRequestDto.class );
        when( slideRequestDto.getBase64Img() ).thenReturn( "Base64 Img" );
        slideServiceImpl.save( slideRequestDto );
    }

    /**
     * Method under test: {@link SlideServiceImpl#save(SlideRequestDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSave5() {
        // TODO: Complete this test.

        SlideRequestDto slideRequestDto = new SlideRequestDto();
        slideRequestDto.setBase64Img( ",Base64 Img" );
        slideServiceImpl.save( slideRequestDto );
    }

    /**
     * Method under test: {@link SlideServiceImpl#save(SlideRequestDto)}
     */
    @Test
    void testSave6() {

        when( amazonClient.uploadFile( any() ) ).thenReturn( "Upload File" );

        Organization organization = new Organization();
        organization.setAboutUsText( "About Us Text" );
        organization.setAddress( "42 Main St" );
        organization.setDeleted( true );
        organization.setEmail( "jane.doe@example.org" );
        organization.setId( "42" );
        organization.setImage( "Image" );
        organization.setName( "Name" );
        organization.setPhone( "4105551212" );
        organization.setTimestamp( mock( Timestamp.class ) );
        organization.setUrlFacebook( "https://example.org/example" );
        organization.setUrlInstagram( "https://example.org/example" );
        organization.setUrlLinkedin( "https://example.org/example" );
        organization.setWelcomeText( "Welcome Text" );

        Slide slide = new Slide();
        slide.setId( "42" );
        slide.setImageUrl( "https://example.org/example" );
        slide.setOrganization( organization );
        slide.setPosition( 1 );
        slide.setText( "Text" );

        Organization organization1 = new Organization();
        organization1.setAboutUsText( "About Us Text" );
        organization1.setAddress( "42 Main St" );
        organization1.setDeleted( true );
        organization1.setEmail( "jane.doe@example.org" );
        organization1.setId( "42" );
        organization1.setImage( "Image" );
        organization1.setName( "Name" );
        organization1.setPhone( "4105551212" );
        organization1.setTimestamp( mock( Timestamp.class ) );
        organization1.setUrlFacebook( "https://example.org/example" );
        organization1.setUrlInstagram( "https://example.org/example" );
        organization1.setUrlLinkedin( "https://example.org/example" );
        organization1.setWelcomeText( "Welcome Text" );
        when( organizationService.get( anyString() ) ).thenReturn( organization1 );

        Organization organization2 = new Organization();
        organization2.setAboutUsText( "About Us Text" );
        organization2.setAddress( "42 Main St" );
        organization2.setDeleted( true );
        organization2.setEmail( "jane.doe@example.org" );
        organization2.setId( "42" );
        organization2.setImage( "Image" );
        organization2.setName( "Name" );
        organization2.setPhone( "4105551212" );
        organization2.setTimestamp( mock( Timestamp.class ) );
        organization2.setUrlFacebook( "https://example.org/example" );
        organization2.setUrlInstagram( "https://example.org/example" );
        organization2.setUrlLinkedin( "https://example.org/example" );
        organization2.setWelcomeText( "Welcome Text" );

        Slide slide1 = new Slide();
        slide1.setId( "42" );
        slide1.setImageUrl( "https://example.org/example" );
        slide1.setOrganization( organization2 );
        slide1.setPosition( 1 );
        slide1.setText( "Text" );
        when( slideRepository.save( any() ) )
                .thenThrow( new RecordException.RecordNotFoundException( "An error occurred" ) );
        when( slideRepository.findTopByOrderByPositionDesc() ).thenReturn( slide1 );

        SlideRequestDto slideRequestDto = new SlideRequestDto();
        slideRequestDto.setBase64Img( ",42" );
        assertThrows( RecordException.RecordNotFoundException.class, () -> slideServiceImpl.save( slideRequestDto ) );
        verify( amazonClient ).uploadFile( any() );
        verify( modelMapper ).map(  any(),  any() );
        verify( organizationService ).get( anyString() );
        verify( slideRepository ).findTopByOrderByPositionDesc();
        verify( slideRepository ).save( any() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#lastPosition()}
     */
    @Test
    void testLastPosition() {

        Organization organization = new Organization();
        organization.setAboutUsText( "About Us Text" );
        organization.setAddress( "42 Main St" );
        organization.setDeleted( true );
        organization.setEmail( "jane.doe@example.org" );
        organization.setId( "42" );
        organization.setImage( "Image" );
        organization.setName( "Name" );
        organization.setPhone( "4105551212" );
        organization.setTimestamp( mock( Timestamp.class ) );
        organization.setUrlFacebook( "https://example.org/example" );
        organization.setUrlInstagram( "https://example.org/example" );
        organization.setUrlLinkedin( "https://example.org/example" );
        organization.setWelcomeText( "Welcome Text" );

        Slide slide = new Slide();
        slide.setId( "42" );
        slide.setImageUrl( "https://example.org/example" );
        slide.setOrganization( organization );
        slide.setPosition( 1 );
        slide.setText( "Text" );
        when( slideRepository.findTopByOrderByPositionDesc() ).thenReturn( slide );
        assertEquals( 1, slideServiceImpl.lastPosition().intValue() );
        verify( slideRepository ).findTopByOrderByPositionDesc();
    }

    /**
     * Method under test: {@link SlideServiceImpl#lastPosition()}
     */
    @Test
    void testLastPosition2() {

        when( slideRepository.findTopByOrderByPositionDesc() )
                .thenThrow( new RecordException.RecordNotFoundException( "An error occurred" ) );
        assertThrows( RecordException.RecordNotFoundException.class, () -> slideServiceImpl.lastPosition() );
        verify( slideRepository ).findTopByOrderByPositionDesc();
    }
}

