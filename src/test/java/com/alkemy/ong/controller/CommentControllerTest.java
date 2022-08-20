package com.alkemy.ong.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.alkemy.ong.dto.CreateCommentDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.News;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
import com.alkemy.ong.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CommentController.class})
@ExtendWith(SpringExtension.class)
class CommentControllerTest {

    @Autowired
    private CommentController commentController;

    @MockBean
    private CommentService commentService;

    /**
     * Method under test: {@link CommentController#actualizarComment(String, CreateCommentDto)}
     */
    @Test
    void testActualizarComment() throws Exception {

        Category category = new Category();
        category.setDeleted( true );
        category.setDescription( "The characteristics of someone or something" );
        category.setId( "42" );
        category.setImage( "Image" );
        category.setName( "Name" );
        category.setTimestamp( mock( Timestamp.class ) );

        News news = new News();
        news.setCategory( category );
        news.setComments( new HashSet<>() );
        news.setContent( "Not all who wander are lost" );
        news.setId( "42" );
        news.setImage( "Image" );
        news.setName( "Name" );
        news.setSoftDelete( true );
        news.setTimestamp( LocalDateTime.of( 1, 1, 1, 1, 1 ) );

        Role role = new Role();
        role.setDescription( "The characteristics of someone or something" );
        role.setId( "42" );
        role.setName( "Name" );
        role.setTimestamp( mock( Timestamp.class ) );
        role.setUsers( new HashSet<>() );

        User user = new User();
        user.setDeleted( true );
        user.setEmail( "jane.doe@example.org" );
        user.setFirstName( "Jane" );
        user.setId( "42" );
        user.setLastName( "Doe" );
        user.setPassword( "iloveyou" );
        user.setPhoto( "alice.liddell@example.org" );
        user.setRole( role );
        user.setTimestamp( mock( Timestamp.class ) );

        Comment comment = new Comment();
        comment.setBody( "Not all who wander are lost" );
        comment.setDeleted( true );
        comment.setId( "42" );
        comment.setNews( news );
        comment.setTimestamp( mock( Timestamp.class ) );
        comment.setUser( user );
        Timestamp timestamp = mock( Timestamp.class );
        when( timestamp.getTime() ).thenReturn( 10L );

        Category category1 = new Category();
        category1.setDeleted( true );
        category1.setDescription( "The characteristics of someone or something" );
        category1.setId( "42" );
        category1.setImage( "Image" );
        category1.setName( "Name" );
        category1.setTimestamp( timestamp );

        News news1 = new News();
        news1.setCategory( category1 );
        news1.setComments( new HashSet<>() );
        news1.setContent( "Not all who wander are lost" );
        news1.setId( "42" );
        news1.setImage( "Image" );
        news1.setName( "Name" );
        news1.setSoftDelete( true );
        news1.setTimestamp( LocalDateTime.of( 1, 1, 1, 1, 1 ) );
        Timestamp timestamp1 = mock( Timestamp.class );
        when( timestamp1.getTime() ).thenReturn( 10L );
        Timestamp timestamp2 = mock( Timestamp.class );
        when( timestamp2.getTime() ).thenReturn( 10L );

        Role role1 = new Role();
        role1.setDescription( "The characteristics of someone or something" );
        role1.setId( "42" );
        role1.setName( "Name" );
        role1.setTimestamp( timestamp2 );
        role1.setUsers( new HashSet<>() );
        Timestamp timestamp3 = mock( Timestamp.class );
        when( timestamp3.getTime() ).thenReturn( 10L );

        User user1 = new User();
        user1.setDeleted( true );
        user1.setEmail( "jane.doe@example.org" );
        user1.setFirstName( "Jane" );
        user1.setId( "42" );
        user1.setLastName( "Doe" );
        user1.setPassword( "iloveyou" );
        user1.setPhoto( "alice.liddell@example.org" );
        user1.setRole( role1 );
        user1.setTimestamp( timestamp3 );

        Comment comment1 = new Comment();
        comment1.setBody( "Not all who wander are lost" );
        comment1.setDeleted( true );
        comment1.setId( "42" );
        comment1.setNews( news1 );
        comment1.setTimestamp( timestamp1 );
        comment1.setUser( user1 );
        when( commentService.actualizarComment( any() ) ).thenReturn( comment1 );
        when( commentService.findById( anyString() ) ).thenReturn( comment );

        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setBody( "Not all who wander are lost" );
        createCommentDto.setNews( "News" );
        createCommentDto.setUser( "User" );
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
                        .string(
                                "{\"id\":\"42\",\"body\":\"Not all who wander are lost\",\"timestamp\":10,\"deleted\":true,\"user\":{\"id\":\"42\","
                                        + "\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\",\"photo\":\"alice"
                                        + ".liddell@example.org\",\"role\":{\"id\":\"42\",\"name\":\"Name\",\"users\":[],\"description\":\"The characteristics"
                                        + " of someone or something\",\"timestamp\":10},\"timestamp\":10,\"deleted\":true},\"news\":{\"id\":\"42\",\"name\":"
                                        + "\"Name\",\"content\":\"Not all who wander are lost\",\"image\":\"Image\",\"timestamp\":[1,1,1,1,1],\"category\":{"
                                        + "\"id\":\"42\",\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"image\":\"Image\","
                                        + "\"timestamp\":10,\"deleted\":true},\"comments\":[],\"softDelete\":true}}" ) );
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

        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setBody( "Not all who wander are lost" );
        createCommentDto.setNews( "News" );
        createCommentDto.setUser( "User" );
        when( commentService.create(  any() ) ).thenReturn( createCommentDto );

        CreateCommentDto createCommentDto1 = new CreateCommentDto();
        createCommentDto1.setBody( "Not all who wander are lost" );
        createCommentDto1.setNews( "News" );
        createCommentDto1.setUser( "User" );
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

    /**
     * Method under test: {@link CommentController#eliminarComment(String)}
     */
    @Test
    void testEliminarComment() throws Exception {

        Category category = new Category();
        category.setDeleted( true );
        category.setDescription( "The characteristics of someone or something" );
        category.setId( "42" );
        category.setImage( "Image" );
        category.setName( "Name" );
        category.setTimestamp( mock( Timestamp.class ) );

        News news = new News();
        news.setCategory( category );
        news.setComments( new HashSet<>() );
        news.setContent( "Not all who wander are lost" );
        news.setId( "42" );
        news.setImage( "Image" );
        news.setName( "Name" );
        news.setSoftDelete( true );
        news.setTimestamp( LocalDateTime.of( 1, 1, 1, 1, 1 ) );

        Role role = new Role();
        role.setDescription( "The characteristics of someone or something" );
        role.setId( "42" );
        role.setName( "Name" );
        role.setTimestamp( mock( Timestamp.class ) );
        role.setUsers( new HashSet<>() );

        User user = new User();
        user.setDeleted( true );
        user.setEmail( "jane.doe@example.org" );
        user.setFirstName( "Jane" );
        user.setId( "42" );
        user.setLastName( "Doe" );
        user.setPassword( "iloveyou" );
        user.setPhoto( "alice.liddell@example.org" );
        user.setRole( role );
        user.setTimestamp( mock( Timestamp.class ) );

        Comment comment = new Comment();
        comment.setBody( "Not all who wander are lost" );
        comment.setDeleted( true );
        comment.setId( "42" );
        comment.setNews( news );
        comment.setTimestamp( mock( Timestamp.class ) );
        comment.setUser( user );
        when( commentService.deleted( anyString() ) ).thenReturn( true );
        when( commentService.findById( anyString() ) ).thenReturn( comment );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/comments/{id}", "42" );
        MockMvcBuilders.standaloneSetup( commentController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() );
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

