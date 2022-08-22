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
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {SlideServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )
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
    void GetAll_al_listar_y_no_encontrar_elementos_debe_devolver_una_lista_vacia() {

        when( slideRepository.findAll() ).thenReturn( new ArrayList<>() );
        assertThat( slideServiceImpl.getAll() ).isEmpty();
        verify( slideRepository ).findAll();
    }

    /**
     * Method under test: {@link SlideServiceImpl#getAll()}
     */
    @Test

    void GetAll_al_listar_y_encontrar_elementos_debe_devolver_una_lista_con_los_elementos() {

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
    void GetAll_al_listar_y_encontrar_elementos_debe_devolver_una_lista_con_exactamente_los_elementos() {

        Organization organization = getOrganization();

        Slide slide = getSlide( organization );

        Organization organization1 = getOrganization();

        Slide slide1 = getSlide( organization1 );

        ArrayList<Slide> slideList = new ArrayList<>();
        slideList.add( slide1 );
        slideList.add( slide );
        when( slideRepository.findAll() ).thenReturn( slideList );
        assertThat(  slideServiceImpl.getAll().size() ).isEqualTo( 2 );
        verify( modelMapper, atLeast( 1 ) ).map( any(), any() );
        verify( slideRepository ).findAll();
    }

    /**
     * Method under test: {@link SlideServiceImpl#getById(String)}
     */
    @Test
    void GetById_al_buscar_un_slide_y_encontrarlo_devuelve_el_slide_no_nulo() {

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
    void Delete_al_eliminar_un_slide_debe_buscar_el_slide_y_al_encontrarlo_no_tirar_excepcion() {

        Organization organization = getOrganization();

        Slide slide = getSlide( organization );
        Optional<Slide> ofResult = Optional.of( slide );
        doNothing().when( slideRepository ).delete( any() );
        when( slideRepository.findById( anyString() ) ).thenReturn( ofResult );
        slideServiceImpl.delete( "42" );
        verify( slideRepository ).findById( anyString() );
        verify( slideRepository ).delete( any() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#delete(String)}
     */
    @Test
    void Delete_al_eliminar_un_slide_debe_buscar_el_slide_y_al_no_encontrarlo_tirar_excepcion() {

        when( slideRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        assertThatThrownBy(  () -> slideServiceImpl.delete( "42" ) ).isInstanceOf( RecordException.RecordNotFoundException.class ).hasMessage( "Slide not found" );
        verify( slideRepository ).findById( anyString() );
        verify( slideRepository, atMost( 0 ) ).delete( any() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#update(String, SlideRequestDto)}
     */
    @Test
    void Update_al_actualizar_y_no_encontrar_tira_una_excepcion_sin_guardar()  {

        when( slideRepository.save( any() ) )
                .thenReturn( Optional.empty());
        when( slideRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        assertThatThrownBy(
                () -> slideServiceImpl.update( "42", new SlideRequestDto() ) ).isInstanceOf( Exception.class ).hasMessage( "Slide not Found" );
        verify( slideRepository, atMost( 0 ) ).save( any() );
        verify( slideRepository ).findById( anyString() );
        verify( slideRepository, atMost( 0 ) ).save( any() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#update(String, SlideRequestDto)}
     */
    @Test
    void Update_al_actualizar_y_encontrar_actualiza_los_valores_no_nulos() throws Exception {
        // TODO: Complete this test.

        Organization organization = getOrganization();

        Slide slide = getSlide( organization );
        Optional<Slide> ofResult = Optional.of( slide );


        Slide slide1 = getSlide( organization );
        when( slideRepository.save( any() ) ).thenReturn( slide1 );

        when(organizationRepository.findById( anyString() ) ).thenReturn( Optional.of( organization ) );
        when( slideRepository.findById( anyString() ) ).thenReturn( ofResult );
        SlideRequestDto slideRequestDto = getRequestDto();
        SlideResponseDto slideResponseDto = slideServiceImpl.update( "42", slideRequestDto );


        assertThat( slideResponseDto ).isNotNull();
        slide.setOrganization( organization );
        assertThat( slideResponseDto.getText() ).isEqualTo( slide.getText() );
        assertThat( slideResponseDto.getPosition() ).isEqualTo( slide.getPosition() );
        assertThat( slideResponseDto.getImageUrl() ).isEqualTo( slide.getImageUrl() );
        assertThat( slideResponseDto.getId() ).isEqualTo( slide.getId() );

        ArgumentCaptor<Slide> argCapture = ArgumentCaptor.forClass( Slide.class );
        verify( slideRepository ).save( argCapture.capture() );
        Slide captureValue = argCapture.getValue();
        assertThat( captureValue.getText() ).isEqualTo( slide.getText() );
        assertThat( captureValue.getPosition() ).isEqualTo( slide.getPosition() );
        assertThat( captureValue.getImageUrl() ).isEqualTo( slide.getImageUrl() );
        Organization captureValueOrganization = captureValue.getOrganization();
        assertThat( captureValueOrganization.getName() )
                .isSameAs( slide.getOrganization().getName() );

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
    void Update_al_actualizar_y_encontrar_no_actualiza_los_valores_nulos() throws Exception {
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
        SlideResponseDto expected = modelMapper.map( slide, SlideResponseDto.class );
        assertThat( slideResponseDto.getText() ).isSameAs( expected.getText() );
        assertThat( slideResponseDto.getPosition() ).isEqualTo( expected.getPosition() );
        assertThat( slideResponseDto.getImageUrl() ).isEqualTo( expected.getImageUrl() );
        assertThat( slideResponseDto.getId() ).isEqualTo( expected.getId() );


        ArgumentCaptor<Slide> argCapture = ArgumentCaptor.forClass( Slide.class );
        verify( slideRepository ).save( argCapture.capture() );
        Slide captureValue = argCapture.getValue();
        assertThat( captureValue.getText() ).isNotNull();
        assertThat( captureValue.getPosition() ).isNotNull();
        assertThat( captureValue.getImageUrl() ).isNotNull();
        Organization captureValueOrganization = captureValue.getOrganization();
        assertThat( captureValueOrganization.getName() )
                .isNotNull();

    }
    /**
     * Method under test: {@link SlideServiceImpl#update(String, SlideRequestDto)}
     */
    @Test
    void Update_al_actualizar_y_no_encontrar_tira_una_excepcion() {
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
    void SlideForOng_al_buscar_slide_para_una_organizacion_y_obtener_lista_vacia_deberia_tirar_excepcion()  {

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
    void SlideForOng_al_buscar_slide_para_una_organizacion_y_obtener_lista_no_vacia_deberia_devolver_la_lista() throws Exception {

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
    void SlideForOng_al_buscar_slide_para_una_organizacion_inexistente_deberia_tirar_excepcion()  {

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
    void Save_al_guardar_sin_posicion_no_deberia_buscar_la_posicion_y_devolver_el_dto_no_nulo() {
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
        verify( slideRepository, atMost( 1 ) ).findTopByOrderByPositionDesc( );
        verify( slideRepository ).save( any() );
    }

    /**
     * Method under test: {@link SlideServiceImpl#save(SlideRequestDto)}
     */
    @Test
    void Save_al_guardar_con_posicion_no_deberia_buscar_la_posicion_y_devolver_el_dto_no_nulo() {
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
        verify( slideRepository, atMost( 0 ) ).findTopByOrderByPositionDesc( );
        verify( slideRepository ).save( any() );

    }

    /**
     * Method under test: {@link SlideServiceImpl#lastPosition()}
     */
    @Test
    void LastPosition_al_buscar_la_posicion_devuelve_la_posicion() {

        Organization organization = getOrganization();

        Slide slide = getSlide( organization );
        when( slideRepository.findTopByOrderByPositionDesc() ).thenReturn( slide );
        assertThat(  slideServiceImpl.lastPosition() ).isEqualTo( 1 );
        verify( slideRepository ).findTopByOrderByPositionDesc();
    }

}

