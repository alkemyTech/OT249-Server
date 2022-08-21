package com.alkemy.ong.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.CreateCommentDto;
import com.alkemy.ong.exceptions.RecordException;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.News;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CommentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link CommentServiceImpl#getAll()}
     */
    @Test
    void testGetAll() {

        when( commentRepository.findAllByOrderByTimestampDesc() ).thenReturn( new ArrayList<>() );
        assertTrue( commentServiceImpl.getAll().isEmpty() );
        verify( commentRepository ).findAllByOrderByTimestampDesc();
    }

    /**
     * Method under test: {@link CommentServiceImpl#getAll()}
     */
    @Test
    void testGetAll2() {

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

        ArrayList<Comment> commentList = new ArrayList<>();
        commentList.add( comment );
        when( commentRepository.findAllByOrderByTimestampDesc() ).thenReturn( commentList );
        assertEquals( 1, commentServiceImpl.getAll().size() );
        verify( commentRepository ).findAllByOrderByTimestampDesc();
        verify( modelMapper ).map(  any(), any() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#getAll()}
     */
    @Test
    void testGetAll3() {

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

        Category category1 = new Category();
        category1.setDeleted( true );
        category1.setDescription( "The characteristics of someone or something" );
        category1.setId( "42" );
        category1.setImage( "Image" );
        category1.setName( "Name" );
        category1.setTimestamp( mock( Timestamp.class ) );

        News news1 = new News();
        news1.setCategory( category1 );
        news1.setComments( new HashSet<>() );
        news1.setContent( "Not all who wander are lost" );
        news1.setId( "42" );
        news1.setImage( "Image" );
        news1.setName( "Name" );
        news1.setSoftDelete( true );
        news1.setTimestamp( LocalDateTime.of( 1, 1, 1, 1, 1 ) );

        Role role1 = new Role();
        role1.setDescription( "The characteristics of someone or something" );
        role1.setId( "42" );
        role1.setName( "Name" );
        role1.setTimestamp( mock( Timestamp.class ) );
        role1.setUsers( new HashSet<>() );

        User user1 = new User();
        user1.setDeleted( true );
        user1.setEmail( "jane.doe@example.org" );
        user1.setFirstName( "Jane" );
        user1.setId( "42" );
        user1.setLastName( "Doe" );
        user1.setPassword( "iloveyou" );
        user1.setPhoto( "alice.liddell@example.org" );
        user1.setRole( role1 );
        user1.setTimestamp( mock( Timestamp.class ) );

        Comment comment1 = new Comment();
        comment1.setBody( "Not all who wander are lost" );
        comment1.setDeleted( true );
        comment1.setId( "42" );
        comment1.setNews( news1 );
        comment1.setTimestamp( mock( Timestamp.class ) );
        comment1.setUser( user1 );

        ArrayList<Comment> commentList = new ArrayList<>();
        commentList.add( comment1 );
        commentList.add( comment );
        when( commentRepository.findAllByOrderByTimestampDesc() ).thenReturn( commentList );
        when( modelMapper.map( any(), any() ) )
                .thenReturn( new CommentDto( "Not all who wander are lost" ) );
        assertEquals( 2, commentServiceImpl.getAll().size() );
        verify( commentRepository ).findAllByOrderByTimestampDesc();
        verify( modelMapper, atLeast( 1 ) ).map( any(), any() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#getAll()}
     */
    @Test
    void testGetAll4() {

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

        ArrayList<Comment> commentList = new ArrayList<>();
        commentList.add( comment );
        when( commentRepository.findAllByOrderByTimestampDesc() ).thenReturn( commentList );
        when( modelMapper.map( any(), any() ) )
                .thenThrow( new RecordException.RecordNotFoundException( "An error occurred" ) );
        assertThrows( RecordException.RecordNotFoundException.class, () -> commentServiceImpl.getAll() );
        verify( commentRepository ).findAllByOrderByTimestampDesc();
        verify( modelMapper ).map( any(), any() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#deleted(String)}
     */
    @Test
    void testDeleted() {

        doNothing().when( commentRepository ).deleteById( anyString() );
        assertTrue( commentServiceImpl.deleted( "42" ) );
        verify( commentRepository ).deleteById( anyString() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#deleted(String)}
     */
    @Test
    void testDeleted2() {

        doThrow( new RecordException.RecordNotFoundException( "An error occurred" ) ).when( commentRepository )
                .deleteById( anyString() );
        assertFalse( commentServiceImpl.deleted( "42" ) );
        verify( commentRepository ).deleteById( anyString() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#create(CreateCommentDto)}
     */
    @Test
    void testCreate() {

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
        when( commentRepository.save( any() ) ).thenReturn( comment );

        Category category1 = new Category();
        category1.setDeleted( true );
        category1.setDescription( "The characteristics of someone or something" );
        category1.setId( "42" );
        category1.setImage( "Image" );
        category1.setName( "Name" );
        category1.setTimestamp( mock( Timestamp.class ) );

        News news1 = new News();
        news1.setCategory( category1 );
        news1.setComments( new HashSet<>() );
        news1.setContent( "Not all who wander are lost" );
        news1.setId( "42" );
        news1.setImage( "Image" );
        news1.setName( "Name" );
        news1.setSoftDelete( true );
        news1.setTimestamp( LocalDateTime.of( 1, 1, 1, 1, 1 ) );
        Optional<News> ofResult = Optional.of( news1 );
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );

        Role role1 = new Role();
        role1.setDescription( "The characteristics of someone or something" );
        role1.setId( "42" );
        role1.setName( "Name" );
        role1.setTimestamp( mock( Timestamp.class ) );
        role1.setUsers( new HashSet<>() );

        User user1 = new User();
        user1.setDeleted( true );
        user1.setEmail( "jane.doe@example.org" );
        user1.setFirstName( "Jane" );
        user1.setId( "42" );
        user1.setLastName( "Doe" );
        user1.setPassword( "iloveyou" );
        user1.setPhoto( "alice.liddell@example.org" );
        user1.setRole( role1 );
        user1.setTimestamp( mock( Timestamp.class ) );
        Optional<User> ofResult1 = Optional.of( user1 );
        when( userRepository.findById( anyString() ) ).thenReturn( ofResult1 );

        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setBody( "Not all who wander are lost" );
        createCommentDto.setNews( "News" );
        createCommentDto.setUser( "User" );
        assertSame( createCommentDto, commentServiceImpl.create( createCommentDto ) );
        verify( commentRepository ).save( any() );
        verify( newsRepository ).findById( anyString() );
        verify( userRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#create(CreateCommentDto)}
     */
    @Test
    void testCreate2() {

        when( commentRepository.save( any() ) )
                .thenThrow( new RecordException.RecordNotFoundException( "An error occurred" ) );

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
        Optional<News> ofResult = Optional.of( news );
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );

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
        Optional<User> ofResult1 = Optional.of( user );
        when( userRepository.findById( anyString() ) ).thenReturn( ofResult1 );

        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setBody( "Not all who wander are lost" );
        createCommentDto.setNews( "News" );
        createCommentDto.setUser( "User" );
        assertThrows( RecordException.RecordNotFoundException.class, () -> commentServiceImpl.create( createCommentDto ) );
        verify( commentRepository ).save( any() );
        verify( newsRepository ).findById( anyString() );
        verify( userRepository ).findById( anyString() );
    }


    /**
     * Method under test: {@link CommentServiceImpl#create(CreateCommentDto)}
     */
    @Test
    void testCreate4() {


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
        Optional<News> ofResult = Optional.of( news );
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );

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
        when( userRepository.findById( anyString() ) ).thenReturn( Optional.empty() );

        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setBody( "Not all who wander are lost" );
        createCommentDto.setNews( "News" );
        createCommentDto.setUser( "User" );
        assertThrows( RecordException.RecordNotFoundException.class, () -> commentServiceImpl.create( createCommentDto ) );
        verify( commentRepository , atMost( 0 )).save( any() );
        verify( newsRepository ).findById( anyString() );
        verify( userRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#create(CreateCommentDto)}
     */
    @Test
    void testCreate3() {

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
        when( commentRepository.save( any() ) ).thenReturn( comment );
        when( newsRepository.findById( anyString() ) ).thenReturn( Optional.empty() );

        Role role1 = new Role();
        role1.setDescription( "The characteristics of someone or something" );
        role1.setId( "42" );
        role1.setName( "Name" );
        role1.setTimestamp( mock( Timestamp.class ) );
        role1.setUsers( new HashSet<>() );

        User user1 = new User();
        user1.setDeleted( true );
        user1.setEmail( "jane.doe@example.org" );
        user1.setFirstName( "Jane" );
        user1.setId( "42" );
        user1.setLastName( "Doe" );
        user1.setPassword( "iloveyou" );
        user1.setPhoto( "alice.liddell@example.org" );
        user1.setRole( role1 );
        user1.setTimestamp( mock( Timestamp.class ) );
        Optional<User> ofResult = Optional.of( user1 );
        when( userRepository.findById( anyString() ) ).thenReturn( ofResult );

        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setBody( "Not all who wander are lost" );
        createCommentDto.setNews( "News" );
        createCommentDto.setUser( "User" );
        assertThrows( RecordException.RecordNotFoundException.class, () -> commentServiceImpl.create( createCommentDto ) );
        verify( newsRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#findById(String)}
     */
    @Test
    void testFindById() {

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
        Optional<Comment> ofResult = Optional.of( comment );
        when( commentRepository.findById( anyString() ) ).thenReturn( ofResult );
        assertSame( comment, commentServiceImpl.findById( "42" ) );
        verify( commentRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#findById(String)}
     */
    @Test
    void testFindById2() {

        when( commentRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        assertNull( commentServiceImpl.findById( "42" ) );
        verify( commentRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#findById(String)}
     */
    @Test
    void testFindById3() {

        when( commentRepository.findById( anyString() ) )
                .thenThrow( new RecordException.RecordNotFoundException( "An error occurred" ) );
        assertThrows( RecordException.RecordNotFoundException.class, () -> commentServiceImpl.findById( "42" ) );
        verify( commentRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#actualizarComment(Comment)}
     */
    @Test
    void testActualizarComment() {

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
        when( commentRepository.save( any() ) ).thenReturn( comment );

        Category category1 = new Category();
        category1.setDeleted( true );
        category1.setDescription( "The characteristics of someone or something" );
        category1.setId( "42" );
        category1.setImage( "Image" );
        category1.setName( "Name" );
        category1.setTimestamp( mock( Timestamp.class ) );

        News news1 = new News();
        news1.setCategory( category1 );
        news1.setComments( new HashSet<>() );
        news1.setContent( "Not all who wander are lost" );
        news1.setId( "42" );
        news1.setImage( "Image" );
        news1.setName( "Name" );
        news1.setSoftDelete( true );
        news1.setTimestamp( LocalDateTime.of( 1, 1, 1, 1, 1 ) );

        Role role1 = new Role();
        role1.setDescription( "The characteristics of someone or something" );
        role1.setId( "42" );
        role1.setName( "Name" );
        role1.setTimestamp( mock( Timestamp.class ) );
        role1.setUsers( new HashSet<>() );

        User user1 = new User();
        user1.setDeleted( true );
        user1.setEmail( "jane.doe@example.org" );
        user1.setFirstName( "Jane" );
        user1.setId( "42" );
        user1.setLastName( "Doe" );
        user1.setPassword( "iloveyou" );
        user1.setPhoto( "alice.liddell@example.org" );
        user1.setRole( role1 );
        user1.setTimestamp( mock( Timestamp.class ) );

        Comment comment1 = new Comment();
        comment1.setBody( "Not all who wander are lost" );
        comment1.setDeleted( true );
        comment1.setId( "42" );
        comment1.setNews( news1 );
        comment1.setTimestamp( mock( Timestamp.class ) );
        comment1.setUser( user1 );
        assertSame( comment, commentServiceImpl.actualizarComment( comment1 ) );
        verify( commentRepository ).save( any() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#commentsByPost(String)}
     */
    @Test
    void testCommentsByPost() {

        ArrayList<Comment> commentList = new ArrayList<>();
        when( commentRepository.findByNews( any() ) ).thenReturn( commentList );

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
        Optional<News> ofResult = Optional.of( news );
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );
        List<Comment> actualCommentsByPostResult = commentServiceImpl.commentsByPost( "42" );
        assertSame( commentList, actualCommentsByPostResult );
        assertTrue( actualCommentsByPostResult.isEmpty() );
        verify( commentRepository ).findByNews( any() );
        verify( newsRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#commentsByPost(String)}
     */
    @Test
    void testCommentsByPost2() {

        when( commentRepository.findByNews( any() ) )
                .thenThrow( new RecordException.RecordNotFoundException( "An error occurred" ) );

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
        Optional<News> ofResult = Optional.of( news );
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );
        assertThrows( RecordException.RecordNotFoundException.class, () -> commentServiceImpl.commentsByPost( "42" ) );
        verify( commentRepository ).findByNews( any() );
        verify( newsRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link CommentServiceImpl#commentsByPost(String)}
     */
    @Test
    void testCommentsByPost3() {

        when( commentRepository.findByNews( any() ) ).thenReturn( new ArrayList<>() );
        when( newsRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        assertTrue( commentServiceImpl.commentsByPost( "42" ).isEmpty() );
        verify( newsRepository ).findById( anyString() );
    }
}

