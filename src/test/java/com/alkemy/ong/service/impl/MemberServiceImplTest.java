package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
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
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MemberServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberServiceImplTest {


    @MockBean
    private MemberRepository memberRepository;

    private MemberServiceImpl memberServiceImpl;

    @SpyBean
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        memberServiceImpl = new MemberServiceImpl( modelMapper, memberRepository );

    }

    /**
     * Method under test: {@link MemberServiceImpl#getMemberById(String)}
     */
    @Test
    void GetMemberById_al_encontrar_o_no_encontrar_siempre_deberia_devolver_nulo() {

        assertThat( memberServiceImpl.getMemberById( "42" ) ).isNull();
    }

    /**
     * Method under test: {@link MemberServiceImpl#getAllMembers(int, String)}
     */
    @Test
    void GetAllMembers_al_paginar_deberia_devolver_los_elementos_disponibles() {

        ArrayList<Member> members = new ArrayList<>();
        members.add( new Member() );
        members.add( new Member() );
        members.add( new Member() );
        PageDto<Member> memberPageDto = new PageDto<>( members, PageUtils.getPageable( 0, "asc" ), 0 );

        when( memberRepository.findAll( any( Pageable.class ) ) ).thenReturn( memberPageDto );
        PageDto<MemberDto> allMembers = memberServiceImpl.getAllMembers( 0, "Order" );
        assertThat( allMembers ).isNotNull();
        assertThat( allMembers.getTotalElements() ).isEqualTo( 3 );

    }

    /**
     * Method under test: {@link MemberServiceImpl#deleteMemberById(String)}
     */
    @Test
    void DeleteMemberById_al_eliminar_y_encontrarlo_deberia_eliminar_pero_no_devolver_nada() {

        Member member = getMember();
        Optional<Member> ofResult = Optional.of( member );
        doNothing().when( memberRepository ).delete( any() );
        when( memberRepository.findById( any() ) ).thenReturn( ofResult );
        memberServiceImpl.deleteMemberById( "42" );
        verify( memberRepository ).findById( any() );
        verify( memberRepository ).delete( any() );
    }

    /**
     * Method under test: {@link MemberServiceImpl#deleteMemberById(String)}
     */
    @Test
    void DeleteMemberById_al_eliminar_y_no_encontrarlo_deberia_tirar_una_excepcion() {
        // TODO: Complete this test.

        doNothing().when( memberRepository ).delete( any() );
        when( memberRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        assertThatThrownBy( () -> memberServiceImpl.deleteMemberById( "42" ) )
                .isInstanceOf( NoSuchElementException.class );
        verify( memberRepository ).findById( anyString() );
        verify( memberRepository, atMost( 0 ) ).delete( any() );
    }

    /**
     * Method under test: {@link MemberServiceImpl#updateMember(MemberDto, String)}
     */
    @Test
    void UpdateMember_al_actualizar_y_encontrarlo_deberia_devolver_un_mensaje() throws EntityNotFoundException {

        Member member = getMember();

        Member member1 = getMember();
        when( memberRepository.getById( anyString() ) ).thenReturn( member );
        when( memberRepository.save( any() ) ).thenReturn( member1 );
        assertThat( memberServiceImpl.updateMember( new MemberDto(), "42" ) ).isEqualTo( "Miembro Actualizado Correctamente" );
        verify( memberRepository ).getById( anyString() );
        ArgumentCaptor<Member> argumentCapture = ArgumentCaptor.forClass( Member.class );
        verify( memberRepository ).save( argumentCapture.capture() );
        Member captureValue = argumentCapture.getValue();
        assertThat( captureValue.getLinkedinUrl() ).isNotNull();
        assertThat( captureValue.getName() ).isNotNull();
        assertThat( captureValue.getInstagramUrl() ).isNotNull();
        assertThat( captureValue.getDescription() ).isNotNull();
        assertThat( captureValue.getIsDelete() ).isNotNull();
        assertThat( captureValue.getName() ).isNotNull();
    }

    /**
     * Method under test: {@link MemberServiceImpl#updateMember(MemberDto, String)}
     */
    @Test
    void UpdateMember_al_actualizar_deberia_solo_actualizar_elementos_no_nulos_y_devolver_un_mensaje() throws EntityNotFoundException {

        Member member = getMember();

        Member member1 = getMember();
        when( memberRepository.getById( anyString() ) ).thenReturn( member );
        when( memberRepository.save( any() ) ).thenReturn( member1 );
        MemberDto memberDto = new MemberDto( "42", "Miembro Actualizado Correctamente", "https://example.org/example",
                null, "https://example.org/example", "Miembro Actualizado Correctamente",
                "The characteristics of someone or something", member1.getTimestamp(), true );
        assertThat( memberServiceImpl.updateMember( memberDto, "42" ) ).isEqualTo( "Miembro Actualizado Correctamente" );
        verify( memberRepository ).getById( anyString() );
        ArgumentCaptor<Member> argumentCapture = ArgumentCaptor.forClass( Member.class );
        verify( memberRepository ).save( argumentCapture.capture() );
        Member captureValue = argumentCapture.getValue();
        assertThat( captureValue.getLinkedinUrl() ).isNotNull();
        assertThat( captureValue.getName() ).isNotNull();
        assertThat( captureValue.getInstagramUrl() ).isNotNull();
        assertThat( captureValue.getDescription() ).isNotNull();
        assertThat( captureValue.getIsDelete() ).isNotNull();
        assertThat( captureValue.getName() ).isNotNull();
    }

    /**
     * Method under test: {@link MemberServiceImpl#createMember(Member)}
     */
    @Test
    void CreateMember_al_guardar_no_devuelve_nada_y_ser_lo_mismo_que_se_pasa() {

        Member member = getMember();
        when( memberRepository.save( any() ) ).thenReturn( member );

        Member member1 = getMember();
        memberServiceImpl.createMember( member1 );
        verify( memberRepository ).save( any() );
        assertThat(  member1.getDescription() ).isEqualTo( "The characteristics of someone or something" );
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        assertThat(  simpleDateFormat.format( member1.getTimestamp() ) ).isEqualTo( "2017-02-03" );
        assertThat(  member1.getName() ).isEqualTo("Name");
        assertThat( member1.getLinkedinUrl() ).isEqualTo( "https://example.org/example" );
        assertThat( member1.getIsDelete() ).isFalse();
        assertThat( member1.getInstagramUrl() ).isEqualTo( "https://example.org/example" );
        assertThat( member1.getImage() ).isEqualTo( "Image" );
        assertThat(  member1.getId() ).isEqualTo( "42" );
        assertThat( member1.getFacebookUrl() ).isEqualTo( "https://example.org/example" );
        ArgumentCaptor<Member> argumentCapture = ArgumentCaptor.forClass( Member.class );
        verify( memberRepository ).save( argumentCapture.capture() );
        Member captureValue = argumentCapture.getValue();
        assertThat( captureValue.getLinkedinUrl() ).isEqualTo( member1.getLinkedinUrl() );
        assertThat( captureValue.getName() ).isEqualTo( member1.getName() );
        assertThat( captureValue.getInstagramUrl() ).isEqualTo( member1.getInstagramUrl() );
        assertThat( captureValue.getDescription() ).isEqualTo( member1.getDescription() );
        assertThat( captureValue.getIsDelete() ).isEqualTo( member1.getIsDelete() );
        assertThat( captureValue.getName() ).isEqualTo( member1.getName() );

    }

    private static Member getMember() {

        Member member = new Member();
        member.setId( "42" );
        member.setDescription( "The characteristics of someone or something" );
        member.setFacebookUrl( "https://example.org/example" );
        member.setImage( "Image" );
        member.setInstagramUrl( "https://example.org/example" );
        member.setIsDelete( false );
        member.setLinkedinUrl( "https://example.org/example" );
        member.setName( "Name" );
        member.setTimestamp( Timestamp.from( Instant.parse( "2017-02-03T10:37:30.00Z" ) ));
        return member;
    }

    /**
     * Method under test: {@link MemberServiceImpl#fillEntity(Member, MemberDto)}
     */
    @Test
    void FillEntity_al_convertir_la_entidad_deberia_devolver_los_mismos_datos() {

        Member member = getMember();
        Member fillEntity = memberServiceImpl.fillEntity( member, new MemberDto() );
        assertThat( fillEntity ).isSameAs( member );
    }

    /**
     * Method under test: {@link MemberServiceImpl#fillEntity(Member, MemberDto)}
     */
    @Test
    void FillEntity_al_convertir_la_entidad_deberia_devolver_los_mismos_datos_en_otro_objecto() {

        Member member = getMember();
        MemberDto memberDto = modelMapper.map( member, MemberDto.class );
        Member fillEntity = memberServiceImpl.fillEntity( member, memberDto );
        assertThat( fillEntity.getName() ).isNotNull().isNotBlank().isEqualTo( member.getName() );
        assertThat( fillEntity.getImage() ).isNotNull().isNotBlank().isEqualTo( member.getImage() );
        assertThat( fillEntity.getDescription() ).isNotNull().isNotBlank().isEqualTo( member.getDescription() );
        assertThat( fillEntity.getIsDelete() ).isNotNull().isEqualTo( member.getIsDelete() );
        assertThat( fillEntity.getFacebookUrl() ).isNotNull().isNotBlank().isEqualTo( member.getFacebookUrl() );
        assertThat( fillEntity.getInstagramUrl() ).isNotNull().isNotBlank().isEqualTo( member.getInstagramUrl() );
        assertThat( fillEntity.getLinkedinUrl() ).isNotNull().isNotBlank().isEqualTo( member.getLinkedinUrl() );
        assertThat( fillEntity ).isSameAs( member );
    }
}

