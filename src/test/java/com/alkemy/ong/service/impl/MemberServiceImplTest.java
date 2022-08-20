package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.utils.PageUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

        assertNull( memberServiceImpl.getMemberById( "42" ) );
    }

    /**
     * Method under test: {@link MemberServiceImpl#getAllMembers(int, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void test_GetAllMembers() {

        when( memberRepository.findAll( (Pageable) any() ) ).thenReturn( new PageImpl<>( new ArrayList<>() ) );
        memberServiceImpl.getAllMembers( 1, "Order" );
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
     * Method under test: {@link MemberServiceImpl#getAllMembers(int, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void test_GetAllMembers3() {

        PageDto<Member> memberPageDto = new PageDto<>( new ArrayList<>(), PageUtils.getPageable( 0, "asc" ), 0 );
        when( memberRepository.findAll( (Pageable) any() ) ).thenReturn( memberPageDto );
        memberServiceImpl.getAllMembers( -1, "Order" );
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
    @Disabled("TODO: Complete this test")
    void test_DeleteMemberById3() {
        // TODO: Complete this test.

        doNothing().when( memberRepository ).delete( any() );
        when( memberRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        memberServiceImpl.deleteMemberById( "42" );
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
    @Disabled("TODO: Complete this test")
    void test_UpdateMember2() throws EntityNotFoundException {
        // TODO: Complete this test.

        Member member = getMember();

        Member member1 = getMember();
        when( memberRepository.getById( anyString() ) ).thenReturn( member );
        when( memberRepository.save( any() ) ).thenReturn( member1 );
        String updateMember = memberServiceImpl.updateMember( null, "42" );
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
        assertEquals( "Miembro Actualizado Correctamente",
                memberServiceImpl
                        .updateMember( memberDto, "42" ) );
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
        assertEquals( "0001-01-02", simpleDateFormat.format( member1.getTimestamp() ) );
        assertEquals( "Name", member1.getName() );
        assertEquals( "https://example.org/example", member1.getLinkedinUrl() );
        assertTrue( member1.getIsDelete() );
        assertEquals( "https://example.org/example", member1.getInstagramUrl() );
        assertEquals( "Image", member1.getImage() );
        assertEquals( "42", member1.getId() );
        assertEquals( "https://example.org/example", member1.getFacebookUrl() );
    }

    private static Member getMember() {

        Member member = new Member();
        member.setDescription( "The characteristics of someone or something" );
        member.setFacebookUrl( "https://example.org/example" );
        member.setId( "42" );
        member.setImage( "Image" );
        member.setInstagramUrl( "https://example.org/example" );
        member.setIsDelete( true );
        member.setLinkedinUrl( "https://example.org/example" );
        member.setName( "Name" );
        member.setTimestamp( Timestamp.from( Instant.from( LocalDateTime.of( 1, 1, 1, 1, 1, 1 ).toInstant( ZoneOffset.UTC ) ) ) );
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

    /**
     * Method under test: {@link MemberServiceImpl#fillEntity(Member, MemberDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFillEntity3() {

        Member member = getMember();
        memberServiceImpl.fillEntity( member, null );
    }

    /**
     * Method under test: {@link MemberServiceImpl#fillEntity(Member, MemberDto)}
     */
    @Test
    void testFillEntity4() {

        Member member = getMember();
        memberServiceImpl.fillEntity( member,
                new MemberDto( "42", "Name", "https://example.org/example", "https://example.org/example",
                        "https://example.org/example", "Image", "The characteristics of someone or something", member.getTimestamp()
                        , true ) );
    }
}

