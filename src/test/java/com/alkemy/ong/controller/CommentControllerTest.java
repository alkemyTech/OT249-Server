package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CreateCommentDto;
import com.alkemy.ong.model.*;
import com.alkemy.ong.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CommentController.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )

class CommentControllerTest {

    private CommentController commentController;

    @MockBean
    private CommentService commentService;

    @BeforeEach
    void setUp() {

        commentController = new CommentController( commentService );

    }

    /**
     * Method under test: {@link CommentController#actualizarComment(String, CreateCommentDto)}
     */
    @Test
    void testActualizarComment() throws Exception {

        Category category = getCategory();
        News news = getNews( category );
        Role role = getRole();
        User user = getUser( role );
        Comment comment = getComment( news, user );
        Category category1 = getCategory();
        News news1 = getNews( category1 );
        Role role1 = getRole();
        User user1 = getUser( role1 );

        Comment comment1 = getComment( news1, user1 );
        when( commentService.actualizarComment( any() ) ).thenReturn( comment1 );
        when( commentService.findById( anyString() ) ).thenReturn( comment );

        CreateCommentDto createCommentDto = getCommentDto();
        String content = (new ObjectMapper()).writeValueAsString( createCommentDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/comments/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( commentController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "application/json" ) )
                .andExpect( MockMvcResultMatchers.content()
                        .json(
                                "{\"id\":\"42\",\"body\":\"Not all who wander are lost\",\"timestamp\":10,\"deleted\":true,\"user\":{\"id\":\"42\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\",\"photo\":\"alice.liddell@example.org\",\"role\":{\"id\":\"42\",\"name\":\"Name\",\"users\":[],\"description\":\"The characteristics of someone or something\",\"timestamp\":10},\"timestamp\":10,\"deleted\":true},\"news\":{\"id\":\"42\",\"name\":\"Name\",\"content\":\"Not all who wander are lost\",\"image\":\"Image\",\"timestamp\":[1,1,1,1,1],\"category\":{\"id\":\"42\",\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"image\":\"Image\",\"timestamp\":10,\"deleted\":true},\"comments\":[],\"softDelete\":true}}\n" ) );
    }


    /**
     * Method under test: {@link CommentController#actualizarComment(String, CreateCommentDto)}
     */
    @Test
    void testActualizarComment3() throws Exception {

        Category category = getCategory();

        News news = getNews( category );

        Role role = getRole();

        User user = getUser( role );

        Comment comment = getComment( news, user );


        when( commentService.actualizarComment( any() ) ).thenThrow( new DataIntegrityViolationException( "Error" ) );
        when( commentService.findById( anyString() ) ).thenReturn( comment );

        CreateCommentDto createCommentDto = getCommentDto();
        String content = (new ObjectMapper()).writeValueAsString( createCommentDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/comments/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( commentController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().is5xxServerError() );
    }

    /**
     * Method under test: {@link CommentController#actualizarComment(String, CreateCommentDto)}
     */
    @Test
    void testActualizarComment2() throws Exception {

        when( commentService.findById( anyString() ) ).thenReturn( null );

        CreateCommentDto createCommentDto = getCommentDto();
        String content = (new ObjectMapper()).writeValueAsString( createCommentDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put( "/comments/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( commentController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isNotFound() );
    }

    /**
     * Method under test: {@link CommentController#commentsInThePost(String)}
     */
    @Test
    void testCommentsInThePost() throws Exception {

        when( commentService.commentsByPost( anyString() ) ).thenReturn( new ArrayList<>() );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/posts/{id}/comments", "42" );
        MockMvcBuilders.standaloneSetup( commentController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "application/json" ) )
                .andExpect( MockMvcResultMatchers.content().string( "[]" ) );
    }

    /**
     * Method under test: {@link CommentController#createComment(CreateCommentDto)}
     */
    @Test
    void testCreateComment() throws Exception {

        CreateCommentDto createCommentDto = getCommentDto();
        when( commentService.create( any() ) ).thenReturn( createCommentDto );

        CreateCommentDto createCommentDto1 = getCommentDto();
        String content = (new ObjectMapper()).writeValueAsString( createCommentDto1 );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/comments" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( commentController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "application/json" ) )
                .andExpect( MockMvcResultMatchers.content()
                        .string( "{\"body\":\"Not all who wander are lost\",\"user\":\"User\",\"news\":\"News\"}" ) );
    }

    private static CreateCommentDto getCommentDto() {

        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setBody( "Not all who wander are lost" );
        createCommentDto.setNews( "News" );
        createCommentDto.setUser( "User" );
        return createCommentDto;
    }

    /**
     * Method under test: {@link CommentController#eliminarComment(String)}
     */
    @Test
    void testEliminarComment() throws Exception {

        Category category = getCategory();

        News news = getNews( category );

        Role role = getRole();

        User user = getUser( role );

        Comment comment = getComment( news, user );
        when( commentService.deleted( anyString() ) ).thenReturn( true );
        when( commentService.findById( anyString() ) ).thenReturn( comment );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/comments/{id}", "42" );
        MockMvcBuilders.standaloneSetup( commentController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() );
    }

    private static Comment getComment(News news, User user) {

        Timestamp timestamp = mock( Timestamp.class );
        when( timestamp.getTime() ).thenReturn( 10L );

        Comment comment = new Comment();
        comment.setBody( "Not all who wander are lost" );
        comment.setDeleted( true );
        comment.setId( "42" );
        comment.setNews( news );
        comment.setTimestamp( timestamp );
        comment.setUser( user );
        return comment;
    }

    /**
     * Method under test: {@link CommentController#eliminarComment(String)}
     */
    @Test
    void testEliminarComment2() throws Exception {

        Category category = getCategory();

        News news = getNews( category );

        Role role = getRole();

        User user = getUser( role );

        getComment( news, user );
        when( commentService.findById( anyString() ) ).thenReturn( null );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/comments/{id}", "42" );
        MockMvcBuilders.standaloneSetup( commentController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isNotFound() );
    }

    private static User getUser(Role role) {

        Timestamp timestamp = mock( Timestamp.class );
        when( timestamp.getTime() ).thenReturn( 10L );

        User user = new User();
        user.setDeleted( true );
        user.setEmail( "jane.doe@example.org" );
        user.setFirstName( "Jane" );
        user.setId( "42" );
        user.setLastName( "Doe" );
        user.setPassword( "iloveyou" );
        user.setPhoto( "alice.liddell@example.org" );
        user.setRole( role );
        user.setTimestamp( timestamp );
        return user;
    }

    private static Role getRole() {

        Timestamp timestamp = mock( Timestamp.class );
        when( timestamp.getTime() ).thenReturn( 10L );

        Role role = new Role();
        role.setDescription( "The characteristics of someone or something" );
        role.setId( "42" );
        role.setName( "Name" );
        role.setTimestamp( timestamp );
        role.setUsers( new HashSet<>() );
        return role;
    }

    private static News getNews(Category category) {

        LocalDateTime localDateTime = LocalDateTime.of( 1, 1, 1, 1, 1 );
        News news = new News();
        news.setCategory( category );
        news.setComments( new HashSet<>() );
        news.setContent( "Not all who wander are lost" );
        news.setId( "42" );
        news.setImage( "Image" );
        news.setName( "Name" );
        news.setSoftDelete( true );
        news.setTimestamp( localDateTime );
        return news;
    }

    private static Category getCategory() {

        Timestamp timestamp = mock( Timestamp.class );
        when( timestamp.getTime() ).thenReturn( 10L );

        Category category = new Category();
        category.setDeleted( true );
        category.setDescription( "The characteristics of someone or something" );
        category.setId( "42" );
        category.setImage( "Image" );
        category.setName( "Name" );
        category.setTimestamp( timestamp );
        return category;
    }

    /**
     * Method under test: {@link CommentController#getAllComments()}
     */
    @Test
    void testGetAllComments() throws Exception {

        when( commentService.getAll() ).thenReturn( new ArrayList<>() );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/comments" );
        MockMvcBuilders.standaloneSetup( commentController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "application/json" ) )
                .andExpect( MockMvcResultMatchers.content().string( "[]" ) );
    }
}

