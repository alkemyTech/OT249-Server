package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.IMemberService;
import com.alkemy.ong.utils.PageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MemberController.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )
class MemberControllerTest {

    @MockBean
    private IMemberService iMemberService;

    private MemberController memberController;

    @MockBean
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {

        memberController = new MemberController( iMemberService, memberRepository );
    }

    /**
     * Method under test: {@link MemberController#createMember(MemberDto)}
     */
    @Test
    void testCreateMember() throws Exception {

        doNothing().when( iMemberService ).createMember( any() );
        MemberDto memberDto = getMemberDto();
        String content = getString( memberDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/members" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( memberController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isCreated() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "Miembro creado con éxito" ) );
    }

    private static String getString(MemberDto memberDto) throws JsonProcessingException {

        return (new ObjectMapper()).writeValueAsString( memberDto );
    }

    private static MemberDto getMemberDto() {

        Timestamp timestamp = Timestamp.from( Instant.from( LocalDateTime.of( 1, 1, 1, 1, 1, 1 ).toInstant( ZoneOffset.UTC ) ) );

        MemberDto memberDto = new MemberDto();
        memberDto.setDescription( "The characteristics of someone or something" );
        memberDto.setFacebookUrl( "https://example.org/example" );
        memberDto.setId( "42" );
        memberDto.setImage( "Image" );
        memberDto.setInstagramUrl( "https://example.org/example" );
        memberDto.setIsDelete( true );
        memberDto.setLinkedinUrl( "https://example.org/example" );
        memberDto.setName( "Name" );
        memberDto.setTimestamp( timestamp );
        return memberDto;
    }

    /**
     * Method under test: {@link MemberController#updateMember(MemberDto, String)}
     */
    @Test
    void testUpdateMember() throws Exception {

        when( iMemberService.updateMember( any(), any() ) ).thenReturn( "2020-03-01" );
        MemberDto memberDto = getMemberDto();
        String content = getString( memberDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/members/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( memberController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "2020-03-01" ) );
    }

    /**
     * Method under test: {@link MemberController#updateMember(MemberDto, String)}
     */
    @Test
    void testUpdateMember2() throws Exception {

        when( iMemberService.updateMember( any(), any() ) ).thenThrow( new EntityNotFoundException( "ERROR" ) );
        MemberDto memberDto = getMemberDto();
        String content = getString( memberDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/members/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( memberController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isNotFound() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "ERROR" ) );
    }

    /**
     * Method under test: {@link MemberController#getAllMembers(int, String)}
     */
    @Test
    void test_GetAllMembers() throws Exception {

        when( iMemberService.getAllMembers( anyInt(), any() ) ).thenReturn( new PageDto<>( new ArrayList<>(), PageUtils.getPageable( 0, "asc" ), 0 ) );
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get( "/members" ).param( "order", "foo" );
        MockHttpServletRequestBuilder requestBuilder = paramResult.param( "page", String.valueOf( 0 ) );
        MockMvcBuilders.standaloneSetup( memberController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().json( "{\"content\":[],\"_links\":null,\"last\":true,\"totalElements\":0,\"totalPages\":0,\"size\":10,\"number\":0,\"first\":true,\"numberOfElements\":0,\"empty\":true}" ) );
    }

    /**
     * Method under test: {@link MemberController#deleteCategory(String)}
     */
    @Test
    void test_DeleteCategory() throws Exception {

        when( memberRepository.existsById( any() ) ).thenReturn( true );
        doNothing().when( iMemberService ).deleteMemberById( any() );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/members/{id}", "42" );
        MockMvcBuilders.standaloneSetup( memberController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "Borrado con éxito" ) );
    }

    /**
     * Method under test: {@link MemberController#deleteCategory(String)}
     */
    @Test
    void test_DeleteCategory2() throws Exception {

        when( memberRepository.existsById( any() ) ).thenReturn( false );
        doNothing().when( iMemberService ).deleteMemberById( any() );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/members/{id}", "42" );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( memberController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isNotFound() )
                .andExpect( MockMvcResultMatchers.content().contentType( "text/plain;charset=ISO-8859-1" ) )
                .andExpect( MockMvcResultMatchers.content().string( "Miembro no encontrado" ) );
    }
}

