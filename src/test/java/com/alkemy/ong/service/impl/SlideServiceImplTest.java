package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.exceptions.RecordException;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.OrganizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {SlideServiceImpl.class})
@ExtendWith(SpringExtension.class)
class SlideServiceImplTest {

    @MockBean
    private AmazonClientImpl amazonClientImpl;

    @SpyBean
    private ModelMapper modelMapper;

    @MockBean
    private OrganizationRepository organizationRepository;

    @MockBean
    private OrganizationService organizationService;

    @MockBean
    private SlideRepository slideRepository;


    private SlideServiceImpl slideServiceImpl;

    @BeforeEach
    void setUp() {
        slideServiceImpl = new SlideServiceImpl( slideRepository, modelMapper, organizationService, amazonClientImpl, organizationRepository );

    }

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

        Organization organization = getOrganization();

        Slide slide = getSlide( organization );

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

        Organization organization = getOrganization();

        Slide slide = getSlide( organization );

        Organization organization1 = getOrganization();

        Slide slide1 = getSlide( organization1 );

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

        Organization organization = getOrganization();

        Slide slide = getSlide( organization );

        ArrayList<Slide> slideList = new ArrayList<>();
        slideList.add( slide );
        when( slideRepository.findAll() ).thenReturn( slideList );
        assertNotNull( slideServiceImpl.getAll() );
        verify( modelMapper ).map( any(), any() );
        verify( slideRepository ).findAll();
    }

    /**
     * Method under test: {@link SlideServiceImpl#getById(String)}
     */
    @Test
    void testGetById() {

        Slide slide = new
                Slide();
        slide.setOrganization( new Organization() );
        when( slideRepository.getById( anyString() ) )
                .thenReturn( slide );
        assertThat( slideServiceImpl.getById( "42" ) ).isNotNull();
        verify( modelMapper , atLeast( 2 )).map( any(), any() );
        verify( slideRepository ).getById( any() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#delete(String)}
     */
    @Test
    void testDelete() {

        Organization organization = getOrganization();

        Slide slide = getSlide( organization );
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

        Organization organization = getOrganization();

        Slide slide = getSlide( organization );
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

        when( slideRepository.save( any() ) )
                .thenReturn( Optional.empty());
        when( slideRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        assertThrows( Exception.class,
                () -> slideServiceImpl.update( "42", new SlideRequestDto() ) );
        verify( slideRepository, atMost( 0 ) ).save( any() );
        verify( slideRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#update(String, SlideRequestDto)}
     */
    @Test
    void testUpdate2() throws Exception {
        // TODO: Complete this test.

        Organization organization = getOrganization();

        Slide slide = getSlide( organization );
        Optional<Slide> ofResult = Optional.of( slide );


        Organization organization1 = getOrganization();

        Slide slide1 = getSlide( organization1 );
        when( slideRepository.save( any() ) ).thenReturn( slide1 );

        when(organizationRepository.findById( anyString() ) ).thenReturn( Optional.of( new Organization() ) );
        when( slideRepository.findById( anyString() ) ).thenReturn( ofResult );
        SlideRequestDto slideRequestDto = getRequestDto();
        SlideResponseDto slideResponseDto = slideServiceImpl.update( "42", slideRequestDto );


        assertThat( slideResponseDto ).isNotNull();
    }

    private static SlideRequestDto getRequestDto() {

        SlideRequestDto slideRequestDto = new SlideRequestDto();
        slideRequestDto.setBase64Img( "22,33" );
        slideRequestDto.setText( "__" );
        slideRequestDto.setPosition( 0 );
        slideRequestDto.setOrgId( "id" );
        return slideRequestDto;
    }

    private static Organization getOrganization() {

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
        return organization1;
    }

    private static Slide getSlide(Organization organization) {

        Slide slide = new Slide();
        slide.setId( "42" );
        slide.setImageUrl( "https://example.org/example" );
        slide.setOrganization( organization );
        slide.setPosition( 1 );
        slide.setText( "Text" );
        return slide;
    }


    /**
     * Method under test: {@link SlideServiceImpl#update(String, SlideRequestDto)}
     */
    @Test
    void testUpdate4() throws Exception {
        // TODO: Complete this test.

        Organization organization = getOrganization();

        Slide slide = getSlide( organization );
        Optional<Slide> ofResult = Optional.of( slide );

        Organization organization1 = getOrganization();

        Slide slide1 = getSlide( organization1 );
        when( slideRepository.save( any() ) ).thenReturn( slide1 );

        when(organizationRepository.findById( anyString() ) ).thenReturn( Optional.of( new Organization() ) );
        when( slideRepository.findById( anyString() ) ).thenReturn( ofResult );
        SlideRequestDto slideRequestDto = new SlideRequestDto();
        SlideResponseDto slideResponseDto = slideServiceImpl.update( "42", slideRequestDto );


        assertThat( slideResponseDto ).isNotNull();
    }
    /**
     * Method under test: {@link SlideServiceImpl#update(String, SlideRequestDto)}
     */
    @Test
    void testUpdate3() {
        // TODO: Complete this test.

        Organization organization = getOrganization();

        Slide slide = getSlide( organization );
        Optional<Slide> ofResult = Optional.of( slide );

        Organization organization1 = getOrganization();

        Slide slide1 = getSlide( organization1 );
        when( slideRepository.save( any() ) ).thenReturn( slide1 );

        when(organizationRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        when( slideRepository.findById( anyString() ) ).thenReturn( ofResult );
        SlideRequestDto slideRequestDto = getRequestDto();
        assertThatThrownBy(  () ->slideServiceImpl.update( "42", slideRequestDto )).isInstanceOf( Exception.class );

    }
    /**
     * Method under test: {@link SlideServiceImpl#slideForOng(String)}
     */
    @Test
    void testSlideForOng()  {

        Organization organization = getOrganization();
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
    void testSlideForOng2() throws Exception {

        Organization organization = getOrganization();
        Optional<Organization> ofResult = Optional.of( organization );
        when( organizationRepository.findById( anyString() ) ).thenReturn( ofResult );
        ArrayList<Slide> slides = new ArrayList<>();
        slides.add( new Slide() );
        when( slideRepository.findByOrganization_idLikeOrderByPositionDesc( anyString() ) )
                .thenReturn( slides );
        assertThat( slideServiceImpl.slideForOng( "42" ) ).isNotNull();
        verify( modelMapper ,  atLeast( 2 )).map(  any(),  any() );
        verify( organizationRepository ).findById( anyString() );
        verify( slideRepository ).findByOrganization_idLikeOrderByPositionDesc( anyString() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#slideForOng(String)}
     */
    @Test
    void testSlideForOng3()  {

        when( organizationRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        when( slideRepository.findByOrganization_idLikeOrderByPositionDesc( anyString() ) ).thenReturn( new ArrayList<>() );
        assertThrows( Exception.class, () -> slideServiceImpl.slideForOng( "42" ) );
        verifyNoInteractions( modelMapper );
        verify( organizationRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#save(SlideRequestDto)}
     */
    @Test
    void testSave() {
        // TODO: Complete this test.

        when( amazonClientImpl.uploadFile( any() ) ).thenReturn( "algo" );
        Slide slide = new Slide();
        slide.setPosition( 0 );
        slide.setId( "id" );
        Organization organization = new Organization();
        slide.setOrganization( organization );
        when( slideRepository.findTopByOrderByPositionDesc() ).thenReturn( slide );
        when( slideRepository.save( any() ) ).thenReturn( slide );
        when( organizationService.get( anyString() ) ).thenReturn( organization );
        SlideRequestDto slideRequestDto = new SlideRequestDto();
        slideRequestDto.setBase64Img( "aaa,aaaa,aaaa" );
        slideRequestDto.setOrgId( "id" );
        SlideResponseDto responseDto = slideServiceImpl.save( slideRequestDto );
        assertThat( responseDto ).isNotNull();
        SlideResponseDto expected = modelMapper.map( slide, SlideResponseDto.class );
        assertThat( responseDto.getText() ).isSameAs( expected.getText() );
        assertThat( responseDto.getPosition() ).isEqualTo( expected.getPosition() + 1 );
        assertThat( responseDto.getImageUrl() ).isEqualTo( "algo" );
        assertThat( responseDto.getId() ).isEqualTo( expected.getId() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#save(SlideRequestDto)}
     */
    @Test
    void testSave2() {
        // TODO: Complete this test.
        when( amazonClientImpl.uploadFile( any() ) ).thenReturn( "algo" );
        Slide slide = new Slide();
        slide.setPosition( 0 );
        slide.setId( "id" );
        Organization organization = new Organization();
        slide.setOrganization( organization );
        when( slideRepository.findTopByOrderByPositionDesc() ).thenReturn( slide );
        when( slideRepository.save( any() ) ).thenReturn( slide );
        when( organizationService.get( anyString() ) ).thenReturn( organization );
        SlideRequestDto slideRequestDto = new SlideRequestDto();
        slideRequestDto.setBase64Img( "aaa,aaaa,aaaa" );
        slideRequestDto.setOrgId( "id" );
        slideRequestDto.setPosition( 0 );
        SlideResponseDto responseDto = slideServiceImpl.save( slideRequestDto );
        assertThat( responseDto ).isNotNull();
        SlideResponseDto expected = modelMapper.map( slide, SlideResponseDto.class );
        assertThat( responseDto.getText() ).isSameAs( expected.getText() );
        assertThat( responseDto.getPosition() ).isEqualTo( expected.getPosition() );
        assertThat( responseDto.getImageUrl() ).isEqualTo( "algo" );
        assertThat( responseDto.getId() ).isEqualTo( expected.getId() );

    }

    /**
     * Method under test: {@link SlideServiceImpl#lastPosition()}
     */
    @Test
    void testLastPosition() {

        Organization organization = getOrganization();

        Slide slide = getSlide( organization );
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

