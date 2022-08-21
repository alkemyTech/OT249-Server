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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void test_GetMemberById() {

        assertThat( memberServiceImpl.getMemberById( "42" ) ).isNull();
    }

    /**
     * Method under test: {@link MemberServiceImpl#getAllMembers(int, String)}
     */
    @Test
    void test_GetAllMembers2() {

        ArrayList<Member> members = new ArrayList<>();
        members.add( new Member() );
        members.add( new Member() );
        members.add( new Member() );
        PageDto<Member> memberPageDto = new PageDto<>( members, PageUtils.getPageable( 0, "asc" ), 0 );

        when( memberRepository.findAll( any( Pageable.class ) ) ).thenReturn( memberPageDto );
        PageDto<MemberDto> allMembers = memberServiceImpl.getAllMembers( 1, "Order" );
        assertThat( allMembers ).isNotNull();
    }

    /**
     * Method under test: {@link MemberServiceImpl#deleteMemberById(String)}
     */
    @Test
    void test_DeleteMemberById() {

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
    void test_DeleteMemberById2() {

        Member member = getMember();
        Optional<Member> ofResult = Optional.of( member );
        doThrow( new EntityNotFoundException( "An error occurred" ) ).when( memberRepository ).delete( any() );
        when( memberRepository.findById( anyString() ) ).thenReturn( ofResult );
        assertThrows( EntityNotFoundException.class, () -> memberServiceImpl.deleteMemberById( "42" ) );
        verify( memberRepository ).findById( anyString() );
        verify( memberRepository ).delete( any() );
    }

    /**
     * Method under test: {@link MemberServiceImpl#deleteMemberById(String)}
     */
    @Test
    void test_DeleteMemberById3() {
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
    void test_UpdateMember() throws EntityNotFoundException {

        Member member = getMember();

        Member member1 = getMember();
        when( memberRepository.getById( anyString() ) ).thenReturn( member );
        when( memberRepository.save( any() ) ).thenReturn( member1 );
        assertEquals( "Miembro Actualizado Correctamente", memberServiceImpl.updateMember( new MemberDto(), "42" ) );
        verify( memberRepository ).getById( anyString() );
        verify( memberRepository ).save( any() );
    }

    /**
     * Method under test: {@link MemberServiceImpl#updateMember(MemberDto, String)}
     */
    @Test
    void test_UpdateMember3() throws EntityNotFoundException {

        Member member = getMember();

        Member member1 = getMember();
        when( memberRepository.getById( anyString() ) ).thenReturn( member );
        when( memberRepository.save( any() ) ).thenReturn( member1 );
        MemberDto memberDto = new MemberDto( "42", "Miembro Actualizado Correctamente", "https://example.org/example",
                "https://example.org/example", "https://example.org/example", "Miembro Actualizado Correctamente",
                "The characteristics of someone or something", member1.getTimestamp(), true );
        assertThat( memberServiceImpl.updateMember( memberDto, "42" ) ).isEqualTo( "Miembro Actualizado Correctamente" );
        verify( memberRepository ).getById( anyString() );
        verify( memberRepository ).save( any() );
    }

    /**
     * Method under test: {@link MemberServiceImpl#updateMember(MemberDto, String)}
     */
    @Test
    void test_UpdateMember4() throws EntityNotFoundException {

        when( memberRepository.getById( anyString() ) ).thenThrow( new EntityNotFoundException( "An error occurred" ) );
        when( memberRepository.save( any() ) ).thenThrow( new EntityNotFoundException( "An error occurred" ) );
        assertThrows( EntityNotFoundException.class, () -> memberServiceImpl.updateMember( new MemberDto(), "42" ) );
        verify( memberRepository ).getById( anyString() );
    }

    /**
     * Method under test: {@link MemberServiceImpl#createMember(Member)}
     */
    @Test
    void test_CreateMember() {

        Member member = getMember();
        when( memberRepository.save( any() ) ).thenReturn( member );

        Member member1 = getMember();
        memberServiceImpl.createMember( member1 );
        verify( memberRepository ).save( any() );
        assertEquals( "The characteristics of someone or something", member1.getDescription() );
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        assertThat(  simpleDateFormat.format( member1.getTimestamp() ) ).isEqualTo( "2017-02-03" );
        assertThat(  member1.getName() ).isEqualTo("Name");
        assertThat( member1.getLinkedinUrl() ).isEqualTo( "https://example.org/example" );
        assertThat( member1.getIsDelete() ).isFalse();
        assertThat( member1.getInstagramUrl() ).isEqualTo( "https://example.org/example" );
        assertThat( member1.getImage() ).isEqualTo( "Image" );
        assertThat(  member1.getId() ).isEqualTo( "42" );
        assertThat( member1.getFacebookUrl() ).isEqualTo( "https://example.org/example" );
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
     * Method under test: {@link MemberServiceImpl#createMember(Member)}
     */
    @Test
    void test_CreateMember2() {

        when( memberRepository.save( any() ) ).thenThrow( new EntityNotFoundException( "An error occurred" ) );

        Member member = getMember();
        assertThrows( EntityNotFoundException.class, () -> memberServiceImpl.createMember( member ) );
        verify( memberRepository ).save( any() );
    }

    /**
     * Method under test: {@link MemberServiceImpl#fillEntity(Member, MemberDto)}
     */
    @Test
    void test_FillEntity() {

        Member member = getMember();
        Member fillEntity = memberServiceImpl.fillEntity( member, new MemberDto() );
        assertThat( fillEntity ).isSameAs( member );
    }

    /**
     * Method under test: {@link MemberServiceImpl#fillEntity(Member, MemberDto)}
     */
    @Test
    void test_FillEntity2() {

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

