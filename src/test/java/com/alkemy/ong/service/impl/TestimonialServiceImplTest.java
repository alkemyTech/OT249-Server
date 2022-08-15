package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.exceptions.BindingResultException;
import com.alkemy.ong.exceptions.RecordException;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.utils.PageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TestimonialServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class TestimonialServiceImplTest {

    @SpyBean
    private ModelMapper modelMapper;

    @MockBean
    private TestimonialRepository testimonialRepository;

    private TestimonialServiceImpl testimonialServiceImpl;

    @BeforeEach
    void setUp() {

        testimonialServiceImpl = new TestimonialServiceImpl( testimonialRepository, modelMapper );
    }

    private static TestimonialDto getTestimonialDto() {

        TestimonialDto testimonialDto = new TestimonialDto();
        testimonialDto.setContent( "Not all who wander are lost" );
        testimonialDto.setId( "42" );
        testimonialDto.setImage( "Image" );
        testimonialDto.setName( "Name" );
        testimonialDto.setSoftDelete( false );
        return testimonialDto;
    }

    private Testimonial getTestimonialWithId() {

        Testimonial testimonial = getTestimonial( false );
        testimonial.setId( "algo" );
        return testimonial;
    }

    private static Testimonial getTestimonial(boolean softDelete) {

        Testimonial testimonial = new Testimonial();
        testimonial.setContent( "Not all who wander are lost" );
        testimonial.setId( "42" );
        testimonial.setImage( "Image" );
        testimonial.setName( "Name" );
        testimonial.setSoftDelete( softDelete );
        testimonial.setTimestamp( Timestamp.from( LocalDateTime.of( 1, 1, 1, 1, 1, 1 ).toInstant( ZoneOffset.UTC ) ) );
        return testimonial;
    }

    private Testimonial getUpdatedTestimonial() {

        Testimonial testimonialWithId = getTestimonialWithId();
        testimonialWithId.setId( "42" );
        testimonialWithId.setName( "updated" );
        testimonialWithId.setImage( "updated" );
        testimonialWithId.setContent( "updated" );
        testimonialWithId.setSoftDelete( false );
        return testimonialWithId;
    }


    /**
     * Method under test: {@link TestimonialServiceImpl#deleteTestimony}
     */
    @Test
    void DeleteTestimony_cuando_se_encuentra_deberia_deberia_borrarlo() {

        Testimonial testimonial = getTestimonial( true );
        Optional<Testimonial> ofResult = Optional.of( testimonial );
        doNothing().when( testimonialRepository ).delete( any() );
        when( testimonialRepository.findById( any() ) ).thenReturn( ofResult );
        testimonialServiceImpl.deleteTestimony( "42" );
        verify( testimonialRepository ).findById( any() );
        verify( testimonialRepository ).delete( any() );
    }


    /**
     * Method under test: {@link TestimonialServiceImpl#deleteTestimony}
     */
    @Test
    void DeleteTestimony_cuando_se_pasa_entidad_nula_deberia_tirar_una_excepcion() {

        Testimonial testimonial = getTestimonial( true );
        Optional<Testimonial> ofResult = Optional.of( testimonial );
        doThrow( new IllegalArgumentException( "Error" ) ).when( testimonialRepository )
                .delete( any() );
        when( testimonialRepository.findById( anyString() ) ).thenReturn( ofResult );
        assertThatThrownBy( () -> testimonialServiceImpl.deleteTestimony( "42" ) ).isInstanceOf( IllegalArgumentException.class ).hasMessage( "Error" );
        verify( testimonialRepository ).findById( any() );
        verify( testimonialRepository ).delete( any() );
    }

    /**
     * Method under test: {@link TestimonialServiceImpl#deleteTestimony}
     */
    @Test
    void DeleteTestimony_cuando_no_se_encuentra_la_entidad_deberia_tirar_una_excepcion() {

        when( testimonialRepository.findById( any() ) ).thenReturn( Optional.empty() );
        assertThatThrownBy( () -> testimonialServiceImpl.deleteTestimony( "42" ) ).isInstanceOf( RecordException.RecordNotFoundException.class ).hasMessage( "Testimony Not found" );
        verify( testimonialRepository ).findById( any() );
        verify( testimonialRepository, atMost( 0 ) ).delete( any() );
    }

    /**
     * Method under test: {@link TestimonialServiceImpl#updateTestimony(String, TestimonialDto, BindingResult)}
     */
    @Test
    void UpdateTestimony_cuando_hay_error_de_validacion_deberia_tirar_una_excepcion() {

        TestimonialDto testimonialDto = getTestimonialDto();
        BindingResult bindingResult = mock( BindingResult.class );
        when( bindingResult.hasFieldErrors() ).thenReturn( true );
        assertThatThrownBy( () -> testimonialServiceImpl.updateTestimony( "42", testimonialDto,
                bindingResult ) ).isInstanceOf( BindingResultException.class ).hasFieldOrProperty( "fieldErrors" );
        verifyNoInteractions( testimonialRepository );
        verifyNoInteractions( modelMapper );
    }

    /**
     * Method under test: {@link TestimonialServiceImpl#updateTestimony(String, TestimonialDto, BindingResult)}
     */
    @Test
    void UpdateTestimony_cuando_no_hay_error_de_validacion_pero_no_se_encuentra_deberia_tirar_excepcion() {

        when( testimonialRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        TestimonialDto testimonialDto = getTestimonialDto();
        assertThatThrownBy( () -> testimonialServiceImpl.updateTestimony( "42",
                testimonialDto, mock( BindingResult.class ) ) ).isInstanceOf( RecordException.RecordNotFoundException.class ).hasMessage( "Testimonial not found" );
        verify( testimonialRepository ).findById( any() );
        verifyNoInteractions( modelMapper );
    }

    /**
     * Method under test: {@link TestimonialServiceImpl#updateTestimony(String, TestimonialDto, BindingResult)}
     */
    @Test
    void UpdateTestimony_cuando_se_pasa_valor_nulo_deberia_tirar_una_excepcion() {

        when( testimonialRepository.findById( any() ) ).thenThrow( new IllegalArgumentException( "Error" ) );
        assertThatThrownBy( () ->
                testimonialServiceImpl.updateTestimony( "42", new TestimonialDto(), mock( BindingResult.class ) )
        )
                .isInstanceOf( IllegalArgumentException.class )
                .hasMessage( "Error" );

    }

    /**
     * Method under test: {@link TestimonialServiceImpl#updateTestimony(String, TestimonialDto, BindingResult)}
     */
    @Test
    void UpdateTestimony_cuando_no_hay_error_de_validacion_y_se_encuentra_deberia_actualizar_y_devolver_la_entidad_actualizada() {

        //given
        Testimonial testimonial = getTestimonial( false );
        TestimonialDto testimonialDto = getTestimonialDto();
        testimonialDto.setContent( "updated" );
        testimonialDto.setImage( "updated" );
        testimonialDto.setName( "updated" );
        Testimonial testimonialSaved = new Testimonial();
        testimonialSaved.setName( testimonialDto.getName() );
        testimonialSaved.setContent( testimonialDto.getContent() );
        testimonialSaved.setImage( testimonialDto.getImage() );
        BindingResult bindingResult = mock( BindingResult.class );

        //when

        when( testimonialRepository.findById( anyString() ) ).thenReturn( Optional.of( testimonial ) );
        when( testimonialRepository.save( any() ) ).thenReturn( testimonialSaved );
        when( bindingResult.hasFieldErrors() ).thenReturn( false );

        //then

        TestimonialDto updateTestimony = testimonialServiceImpl.updateTestimony( "42", testimonialDto, bindingResult );

        assertThat( updateTestimony.getName() )
                .isEqualTo( testimonialDto.getName() )
                .isNotEqualTo( testimonial.getName() );

        assertThat( updateTestimony.getImage() )
                .isEqualTo( testimonialDto.getImage() )
                .isNotEqualTo( testimonial.getImage() );

        assertThat( updateTestimony.getContent() ).isEqualTo( testimonialDto.getContent() ).isNotEqualTo( testimonial.getContent() );
        verify( testimonialRepository ).findById( anyString() );
        verify( testimonialRepository ).save( any() );

    }

    /**
     * Method under test: {@link TestimonialServiceImpl#updateTestimony(String, TestimonialDto, BindingResult)}
     */
    @Test
    void UpdateTestimony_cuando_no_hay_error_de_validacion_unicamente_deberia_actualizar_lo_posible() {

        //given
        Testimonial testimonial = getTestimonial( true );
        Testimonial testimonialWithId = getUpdatedTestimonial();
        ArgumentCaptor<Testimonial> argumentCaptor = ArgumentCaptor.forClass( Testimonial.class );
        TestimonialDto testimonialDto = getTestimonialDto();
        testimonialDto.setName( "updated" );
        testimonialDto.setImage( "updated" );
        testimonialDto.setContent( "updated" );
        testimonialDto.setSoftDelete( false );
        BindingResult bindingResult = mock( BindingResult.class );

        //when
        when( bindingResult.hasFieldErrors() ).thenReturn( false );
        when( testimonialRepository.save( argumentCaptor.capture() ) ).thenReturn( testimonialWithId );
        when( testimonialRepository.findById( anyString() ) ).thenReturn( Optional.of( testimonial ) );

        TestimonialDto actual = testimonialServiceImpl.updateTestimony( "42", testimonialDto, bindingResult );

        //then
        Testimonial captorValue = argumentCaptor.getValue();
        assertThat( actual ).isNotNull();
        assertThat( actual.getId() )
                .isEqualTo( "42" );
        assertThat( actual.getName() )
                .isEqualTo( testimonialDto.getName() )
                .isEqualTo( captorValue.getName() )
                .isNotEqualTo( testimonial.getName() );
        assertThat( actual.getImage() )
                .isEqualTo( testimonialDto.getImage() )
                .isEqualTo( captorValue.getImage() )
                .isNotEqualTo( testimonial.getName() );
        assertThat( actual.getContent() )
                .isEqualTo( testimonialDto.getContent() )
                .isEqualTo( captorValue.getContent() )
                .isNotEqualTo( testimonial.getContent() );

        verify( testimonialRepository ).save( any() );
        verify( modelMapper, atLeast( 1 ) ).map( any(), any() );
    }


    /**
     * Method under test: {@link TestimonialServiceImpl#createTestimony(TestimonialDto, BindingResult)}
     */
    @Test
    void CreateTestimony_cuando_hay_errores_de_validacion_devuelve_una_excepcion() {

        TestimonialDto testimonialDto = getTestimonialDto();
        BindingResult bindingResult = mock( BindingResult.class );
        when( bindingResult.hasFieldErrors() ).thenReturn( true );
        assertThatThrownBy(
                () -> testimonialServiceImpl.createTestimony( testimonialDto, bindingResult ) ).isInstanceOf( BindingResultException.class );
        verifyNoInteractions( modelMapper );
        verifyNoInteractions( testimonialRepository );
        verify( bindingResult ).hasFieldErrors();
    }

    /**
     * Method under test: {@link TestimonialServiceImpl#createTestimony(TestimonialDto, BindingResult)}
     */
    @Test
    void CreateTestimony_cuando_se_pasa_una_entidad_valida_deberia_persistir_la_entidad_y_devolverla() {

        Testimonial testimonial = getTestimonial( true );
        Testimonial testimonialWithId = getTestimonialWithId();
        ArgumentCaptor<Testimonial> argumentCaptor = ArgumentCaptor.forClass( Testimonial.class );
        when( testimonialRepository.save( argumentCaptor.capture() ) ).thenReturn( testimonialWithId );
        TestimonialDto testimonialDto = getTestimonialDto();
        BindingResult bindingResult = mock( BindingResult.class );
        when( bindingResult.hasFieldErrors() ).thenReturn( false );
        TestimonialDto actual = testimonialServiceImpl.createTestimony( testimonialDto, bindingResult );
        assertThat( actual ).isNotNull();
        Testimonial captorValue = argumentCaptor.getValue();
        assertThat( actual.getId() )
                .isNotEqualTo( testimonial.getId() )
                .isNotEqualTo( captorValue.getId() );
        assertThat( actual.getName() )
                .isEqualTo( testimonial.getName() )
                .isEqualTo( captorValue.getName() );
        assertThat( actual.getContent() )
                .isEqualTo( testimonial.getContent() )
                .isEqualTo( captorValue.getContent() );
        assertThat( actual.getSoftDelete() )
                .isNotEqualTo( testimonial.getSoftDelete() )
                .isEqualTo( captorValue.getSoftDelete() )
                .isFalse();

        verify( testimonialRepository ).save( any() );
        verify( modelMapper, atLeast( 1 ) ).map( any(), any() );
    }

    /**
     * Method under test: {@link TestimonialServiceImpl#createTestimony(TestimonialDto, BindingResult)}
     */
    @Test
    void CreateTestimony_cuando_hay_errores_de_validacion_deberia_tirar_una_excepcion() {

        TestimonialDto testimonialDto = getTestimonialDto();
        BindingResult bindingResult = mock( BindingResult.class );
        when( bindingResult.getFieldErrors() ).thenReturn( new ArrayList<>() );
        when( bindingResult.hasFieldErrors() ).thenReturn( true );
        assertThatThrownBy(
                () -> testimonialServiceImpl.createTestimony( testimonialDto, bindingResult ) )
                .isInstanceOf( BindingResultException.class );
        verify( bindingResult ).hasFieldErrors();
        verify( bindingResult ).getFieldErrors();
        verifyNoInteractions( testimonialRepository );
    }

    /**
     * Method under test: {@link TestimonialServiceImpl#createTestimony(TestimonialDto, BindingResult)}
     */
    @Test
    void CreateTestimony_deberia_tirar_una_excepcion_cuando_se_pasa_un_elemento_nulo() {

        when( testimonialRepository.save( any( Testimonial.class ) ) ).thenThrow( new IllegalArgumentException( "Error" ) );
        TestimonialDto testimonialDto = getTestimonialDto();
        BindingResult bindingResult = mock( BindingResult.class );
        when( bindingResult.hasFieldErrors() ).thenReturn( false );
        assertThatThrownBy(
                () -> testimonialServiceImpl.createTestimony( testimonialDto, bindingResult ) )
                .isInstanceOf( IllegalArgumentException.class )
                .hasMessage( "Error" );
    }

    /**
     * Method under test: {@link TestimonialServiceImpl#findById}
     */
    @Test
    void FindById_cuando_encuentra_la_entidad_deberia_devolverla() {

        Testimonial testimonial = getTestimonial( true );
        testimonial.setId( "42" );
        Optional<Testimonial> ofResult = Optional.of( testimonial );
        when( testimonialRepository.findById( any() ) ).thenReturn( ofResult );
        TestimonialDto testimonialDto = getTestimonialDto();
        testimonialDto.setId( "42" );
        TestimonialDto actual = testimonialServiceImpl.findById( "42" );

        assertThat( actual.getId() ).isEqualTo( testimonialDto.getId() );
        assertThat( actual.getContent() ).isEqualTo( testimonialDto.getContent() );
        assertThat( actual.getName() ).isEqualTo( testimonialDto.getName() );
        assertThat( actual.getImage() ).isEqualTo( testimonialDto.getImage() );
        verify( testimonialRepository ).findById( any() );
        verify( modelMapper ).map( any(), any() );
    }

    /**
     * Method under test: {@link TestimonialServiceImpl#findById}
     */
    @Test
    void FindById_cuando_se_pasa_un_nulo_deberia_tirar_excepcion() {

        when( testimonialRepository.findById( any() ) ).thenThrow( new IllegalArgumentException( "error" ) );
        assertThatThrownBy( () -> testimonialServiceImpl.findById( null ) ).isInstanceOf( IllegalArgumentException.class ).hasMessage( "error" );
        verify( testimonialRepository ).findById( any() );
        verifyNoInteractions( modelMapper );
    }

    /**
     * Method under test: {@link TestimonialServiceImpl#findById}
     */
    @Test
    void FindById_cuando_no_se_encuentra_la_entidad_deberia_tirar_excepcion() {

        when( testimonialRepository.findById( any() ) ).thenReturn( Optional.empty() );
        assertThatThrownBy( () -> testimonialServiceImpl.findById( "42" ) ).isInstanceOf( RecordException.RecordNotFoundException.class ).hasMessage( "Testimonial not found" );
        verify( testimonialRepository ).findById( anyString() );
        verifyNoInteractions( modelMapper );
    }

    /**
     * Method under test: {@link TestimonialServiceImpl#getAllTestimonials(int, String)}
     */
    @Test
    void GetAllTestimonials_cuando_se_pasan_pagina_valida_deberia_devolver_paginado() {

        Testimonial testimonial = getTestimonial( false );
        List<Testimonial> testimonials = List.of( testimonial, testimonial, testimonial );
        PageImpl<Testimonial> testimonialPage = new PageImpl<>( testimonials, PageUtils.getPageable( 0, "asc" ), 3 );
        when( testimonialRepository.findAll( any( Pageable.class ) ) ).thenReturn( testimonialPage );
        PageDto<TestimonialDto> allTestimonials = testimonialServiceImpl.getAllTestimonials( 1, "Order" );
        assertThat( allTestimonials.getPageable().getPageNumber() ).isEqualTo( 0 );
        assertThat( allTestimonials.getTotalElements() ).isEqualTo( 3 );
        assertThat( allTestimonials.getSize() ).isEqualTo( 10 );
        assertThat( allTestimonials.getContent().size() ).isEqualTo( 3 );
    }

    /**
     * Method under test: {@link TestimonialServiceImpl#getAllTestimonials(int, String)}
     */
    @Test
    void GetAllTestimonials_cuando_se_pasan_pagina_no_valida_deberia_tirar_una_excepcion() {

        assertThatThrownBy( () -> testimonialServiceImpl.getAllTestimonials( -1, "Order" ) )
                .isInstanceOf( IllegalArgumentException.class )
                .hasMessageEndingWith( "Page index must not be less than zero!" );
    }

    /**
     * Method under test: {@link TestimonialServiceImpl#getAllTestimonials(int, String)}
     */
    @Test
    void GetAllTestimonials_cuando_se_pasan_pagina_valida_pero_no_hay_elementos_deberia_tirar_devolver_vacia() {

        List<Testimonial> testimonials = new ArrayList<>();
        PageImpl<Testimonial> testimonialPage = new PageImpl<>( testimonials, PageUtils.getPageable( 1, "asc" ), 0 );
        when( testimonialRepository.findAll( any( Pageable.class ) ) ).thenReturn( testimonialPage );
        PageDto<TestimonialDto> allTestimonials = testimonialServiceImpl.getAllTestimonials( 1, "Order" );
        assertThat( allTestimonials.getTotalElements() ).isEqualTo( 0 );
        assertThat( allTestimonials.getPageable().getPageNumber() ).isEqualTo( 1 );
        assertThat( allTestimonials.getSize() ).isEqualTo( 10 );
    }
}

