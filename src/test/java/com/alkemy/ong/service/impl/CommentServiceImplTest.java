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

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CommentServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )

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

        Category category = getCategory();

        News news = getNews( category );

        Role role = getRole();

        User user = getUser( role );

        Comment comment = getComment( news, user );

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

        Category category = getCategory();

        News news = getNews( category );

        Role role = getRole();

        User user = getUser( role );

        Comment comment = getComment( news, user );

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

        Category category = getCategory();

        News news = getNews( category );

        Role role = getRole();

        User user = getUser( role );

        Comment comment = getComment( news, user );
        when( commentRepository.save( any() ) ).thenReturn( comment );

        Category category1 = getCategory();

        News news1 = getNews( category1 );
        Optional<News> ofResult = Optional.of( news1 );
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );

        Role role1 = getRole();

        User user1 = getUser( role1 );
        Optional<User> ofResult1 = Optional.of( user1 );
        when( userRepository.findById( anyString() ) ).thenReturn( ofResult1 );

        CreateCommentDto createCommentDto = getCommentDto();
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

        Category category = getCategory();

        News news = getNews( category );
        Optional<News> ofResult = Optional.of( news );
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );

        Role role = getRole();

        User user = getUser( role );
        Optional<User> ofResult1 = Optional.of( user );
        when( userRepository.findById( anyString() ) ).thenReturn( ofResult1 );

        CreateCommentDto createCommentDto = getCommentDto();
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


        Category category = getCategory();

        News news = getNews( category );
        Optional<News> ofResult = Optional.of( news );
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );

        Role role = getRole();

        getUser( role );
        when( userRepository.findById( anyString() ) ).thenReturn( Optional.empty() );

        CreateCommentDto createCommentDto = getCommentDto();
        assertThrows( RecordException.RecordNotFoundException.class, () -> commentServiceImpl.create( createCommentDto ) );
        verify( commentRepository , atMost( 0 )).save( any() );
        verify( newsRepository ).findById( anyString() );
        verify( userRepository ).findById( anyString() );
    }

    private static Role getRole() {

        Role role = new Role();
        role.setDescription( "The characteristics of someone or something" );
        role.setId( "42" );
        role.setName( "Name" );
        role.setTimestamp( mock( Timestamp.class ) );
        role.setUsers( new HashSet<>() );
        return role;
    }

    private static News getNews(Category category) {

        News news = new News();
        news.setCategory( category );
        news.setComments( new HashSet<>() );
        news.setContent( "Not all who wander are lost" );
        news.setId( "42" );
        news.setImage( "Image" );
        news.setName( "Name" );
        news.setSoftDelete( true );
        news.setTimestamp( LocalDateTime.of( 1, 1, 1, 1, 1 ) );
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
     * Method under test: {@link CommentServiceImpl#create(CreateCommentDto)}
     */
    @Test
    void testCreate3() {

        Category category = getCategory();

        News news = getNews( category );

        Role role = getRole();

        User user = getUser( role );

        Comment comment = getComment( news, user );
        when( commentRepository.save( any() ) ).thenReturn( comment );
        when( newsRepository.findById( anyString() ) ).thenReturn( Optional.empty() );

        Role role1 = getRole();

        User user1 = getUser( role1 );
        Optional<User> ofResult = Optional.of( user1 );
        when( userRepository.findById( anyString() ) ).thenReturn( ofResult );

        CreateCommentDto createCommentDto = getCommentDto();
        assertThrows( RecordException.RecordNotFoundException.class, () -> commentServiceImpl.create( createCommentDto ) );
        verify( newsRepository ).findById( anyString() );
    }

    private static CreateCommentDto getCommentDto() {

        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setBody( "Not all who wander are lost" );
        createCommentDto.setNews( "News" );
        createCommentDto.setUser( "User" );
        return createCommentDto;
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

    /**
     * Method under test: {@link CommentServiceImpl#findById(String)}
     */
    @Test
    void testFindById() {

        Category category = getCategory();

        News news = getNews( category );

        Role role = getRole();

        User user = getUser( role );

        Comment comment = getComment( news, user );
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

        Category category = getCategory();

        News news = getNews( category );

        Role role = getRole();

        User user = getUser( role );

        Comment comment = getComment( news, user );
        when( commentRepository.save( any() ) ).thenReturn( comment );

        Category category1 = getCategory();

        News news1 = getNews( category1 );

        Role role1 = getRole();

        User user1 = getUser( role1 );

        Comment comment1 = getComment( news1, user1 );
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

        Category category = getCategory();

        News news = getNews( category );
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

        Category category = getCategory();

        News news = getNews( category );
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

