package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.LoginRequestDTO;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.dto.RoleDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.utils.JwtUtil;
import com.alkemy.ong.utils.PageUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private EmailServiceImpl emailServiceImpl;

    @MockBean
    private JwtUtil jwtUtil;

    @SpyBean
    private ModelMapper modelMapper;

    @MockBean
    private UserRepository userRepository;


    @Autowired
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#guardarUsuario(User)}
     */
    @Test
    void testGuardarUsuario()  {

        doNothing().when( emailServiceImpl ).WelcomeMail( anyString(), anyString() );

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
        when( userRepository.save( any() ) ).thenReturn( user );

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
        assertSame( user, userServiceImpl.guardarUsuario( user1 ) );
        verify( emailServiceImpl ).WelcomeMail( anyString(), anyString() );
        verify( userRepository ).save( any() );
    }

    /**
     * Method under test: {@link UserServiceImpl#guardarUsuario(User)}
     */
    @Test
    void testGuardarUsuario2()  {

        doThrow( new UsernameNotFoundException( "Msg" ) ).when( emailServiceImpl ).WelcomeMail( anyString(), anyString() );

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
        when( userRepository.save( any() ) ).thenReturn( user );

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
        assertThrows( UsernameNotFoundException.class, () -> userServiceImpl.guardarUsuario( user1 ) );
        verify( emailServiceImpl ).WelcomeMail( anyString(), anyString() );
        verify( userRepository ).save( any() );
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers(int, String)}
     */
    @Test
    void testGetAllUsers() {
        // TODO: Complete this test.

        ArrayList<User> arrayList = new ArrayList<>();
        arrayList.add( new User() );
        when( userRepository.findAll( any( Pageable.class) )).thenReturn( new PageImpl<>( arrayList, PageUtils.getPageable( 0,"" ),0 ) );
        PageDto<UserDto> actualPage = userServiceImpl.getAllUsers( 1, "Order" );
        assertThat(actualPage).isNotNull();

    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers(int, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllUsers2() {
        // TODO: Complete this test.

        when( userRepository.findAll( (Pageable) any() ) ).thenReturn( null );
        userServiceImpl.getAllUsers( 1, "Order" );
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers(int, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllUsers3() {
        // TODO: Complete this test.

        when( userRepository.findAll( (Pageable) any() ) ).thenReturn( new PageImpl<>( new ArrayList<>() ) );
        userServiceImpl.getAllUsers( -1, "Order" );
    }

    /**
     * Method under test: {@link UserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {

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
        Optional<User> ofResult = Optional.of( user );
        when( userRepository.findByEmail( anyString() ) ).thenReturn( ofResult );
        assertThrows( UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername( "foo" ) );
        verify( userRepository ).findByEmail( anyString() );
    }

    /**
     * Method under test: {@link UserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {

        when( userRepository.findByEmail( any() ) ).thenReturn( Optional.empty() );

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
        assertThrows( UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername( "foo" ) );
        verifyNoInteractions( modelMapper );
        verify( userRepository ).findByEmail(  any() );
    }

    /**
     * Method under test: {@link UserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException {


        Role role = new Role();
        role.setDescription( "The characteristics of someone or something" );
        role.setId( "42" );
        role.setName( "Name" );
        role.setTimestamp( mock( Timestamp.class ) );
        role.setUsers( new HashSet<>() );
        User user = new User();
        user.setDeleted( false );
        user.setEmail( "jane.doe@example.org" );
        user.setFirstName( "Jane" );
        user.setId( "42" );
        user.setLastName( "Doe" );
        user.setPassword( "iloveyou" );
        user.setPhoto( "alice.liddell@example.org" );
        user.setRole( role );
        user.setTimestamp( mock( Timestamp.class ) );
        when( userRepository.findByEmail( any() ) ).thenReturn( Optional.of( user ) );

        assertThat( userServiceImpl.loadUserByUsername( "foo" ) ).isNotNull();
        verify( modelMapper, atLeast( 2 )).map( any(), any() );
        verify( userRepository ).findByEmail(  any() );
    }

    /**
     * Method under test: {@link UserServiceImpl#findById(String)}
     */
    @Test
    void testFindById() {

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
        Optional<User> ofResult = Optional.of( user );
        when( userRepository.findById( anyString() ) ).thenReturn( ofResult );
        Optional<User> actualFindByIdResult = userServiceImpl.findById( "42" );
        assertSame( ofResult, actualFindByIdResult );
        assertTrue( actualFindByIdResult.isPresent() );
        verify( userRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link UserServiceImpl#findById(String)}
     */
    @Test
    void testFindById2() {

        when( userRepository.findById( anyString() ) ).thenThrow( new UsernameNotFoundException( "Msg" ) );
        assertThrows( UsernameNotFoundException.class, () -> userServiceImpl.findById( "42" ) );
        verify( userRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(String)}
     */
    @Test
    void testDeleteUser() {

        doNothing().when( userRepository ).deleteById( anyString() );
        assertTrue( userServiceImpl.deleteUser( "42" ) );
        verify( userRepository ).deleteById( anyString() );
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(String)}
     */
    @Test
    void testDeleteUser2() {

        doThrow( new UsernameNotFoundException( "Msg" ) ).when( userRepository ).deleteById( anyString() );
        assertFalse( userServiceImpl.deleteUser( "42" ) );
        verify( userRepository ).deleteById( anyString() );
    }

    /**
     * Method under test: {@link UserServiceImpl#login(LoginRequestDTO)}
     */
    @Test
    void testLogin() throws AuthenticationException {

        when( jwtUtil.generateToken( any() ) ).thenReturn( "ABC123" );

        UserDto.UserPagedDto userPagedDto = new UserDto.UserPagedDto();
        userPagedDto.setDeleted( true );
        userPagedDto.setEmail( "jane.doe@example.org" );
        userPagedDto.setFirstName( "Jane" );
        userPagedDto.setLastName( "Doe" );
        userPagedDto.setPassword( "iloveyou" );
        userPagedDto.setPhoto( "alice.liddell@example.org" );
        userPagedDto.setRole( new RoleDto() );
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken( userPagedDto,
                "Credentials" );

        when( authenticationManager.authenticate( any() ) ).thenReturn( testingAuthenticationToken );
        assertEquals( "ABC123", userServiceImpl.login( new LoginRequestDTO( "jane.doe@example.org", "iloveyou" ) ).getJwt() );
        verify( jwtUtil ).generateToken( any() );
        verify( authenticationManager ).authenticate( any() );
    }

    /**
     * Method under test: {@link UserServiceImpl#login(LoginRequestDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testLogin2() throws AuthenticationException {
        // TODO: Complete this test.

        when( jwtUtil.generateToken( any() ) ).thenReturn( "ABC123" );
        when( authenticationManager.authenticate( any() ) ).thenReturn( null );
        userServiceImpl.login( new LoginRequestDTO( "jane.doe@example.org", "iloveyou" ) );
    }

    /**
     * Method under test: {@link UserServiceImpl#login(LoginRequestDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testLogin3() throws AuthenticationException {
        // TODO: Complete this test.

        when( jwtUtil.generateToken( any() ) ).thenReturn( "ABC123" );

        UserDto.UserPagedDto userPagedDto = new UserDto.UserPagedDto();
        userPagedDto.setDeleted( true );
        userPagedDto.setEmail( "jane.doe@example.org" );
        userPagedDto.setFirstName( "Jane" );
        userPagedDto.setLastName( "Doe" );
        userPagedDto.setPassword( "iloveyou" );
        userPagedDto.setPhoto( "alice.liddell@example.org" );
        userPagedDto.setRole( new RoleDto() );
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken( userPagedDto,
                "Credentials" );

        when( authenticationManager.authenticate( any() ) ).thenReturn( testingAuthenticationToken );
        userServiceImpl.login( null );
    }

    /**
     * Method under test: {@link UserServiceImpl#login(LoginRequestDTO)}
     */
    @Test
    void testLogin4() throws AuthenticationException {

        when( jwtUtil.generateToken( any() ) ).thenReturn( "ABC123" );

        UserDto.UserPagedDto userPagedDto = new UserDto.UserPagedDto();
        userPagedDto.setDeleted( true );
        userPagedDto.setEmail( "jane.doe@example.org" );
        userPagedDto.setFirstName( "Jane" );
        userPagedDto.setLastName( "Doe" );
        userPagedDto.setPassword( "iloveyou" );
        userPagedDto.setPhoto( "alice.liddell@example.org" );
        userPagedDto.setRole( new RoleDto() );
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken( userPagedDto,
                "Credentials" );

        when( authenticationManager.authenticate( any() ) ).thenReturn( testingAuthenticationToken );
        LoginRequestDTO loginRequestDTO = mock( LoginRequestDTO.class );
        when( loginRequestDTO.getEmail() ).thenThrow( new BadCredentialsException( "Msg" ) );
        when( loginRequestDTO.getPassword() ).thenThrow( new BadCredentialsException( "Msg" ) );
        assertThrows( BadCredentialsException.class, () -> userServiceImpl.login( loginRequestDTO ) );
        verify( loginRequestDTO ).getEmail();
    }

    /**
     * Method under test: {@link UserServiceImpl#validarId(String)}
     */
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testValidarId() throws Exception {
        // TODO: Complete this test.
        User user = new User();
        Role role = new Role();
        role.setName( "ADMIN" );
        user.setRole( role );
        when( userRepository.findByEmail( anyString() ) ).
        thenReturn( Optional.of( user ) );
        boolean validarId = userServiceImpl.validarId( "42" );
        assertThat( validarId ).isTrue();
    }

    /**
     * Method under test: {@link UserServiceImpl#validarId(String)}
     */
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testValidarId3() throws Exception {
        // TODO: Complete this test.
        User user = new User();
        user.setId( "ddd" );
        Role role = new Role();
        role.setName( "USER" );
        user.setRole( role );
        when( userRepository.findByEmail( anyString() ) ).
                thenReturn( Optional.of( user ) );
        boolean validarId = userServiceImpl.validarId( "42" );
        assertThat( validarId ).isFalse();
    }

    /**
     * Method under test: {@link UserServiceImpl#validarId(String)}
     */
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testValidarId4() throws Exception {
        // TODO: Complete this test.
        User user = new User();
        user.setId( "ddd" );
        Role role = new Role();
        role.setName( "USER" );
        user.setRole( role );
        when( userRepository.findByEmail( anyString() ) ).
                thenReturn( Optional.empty( ) );
        assertThatThrownBy(  () ->userServiceImpl.validarId( "42" )).isInstanceOf( Exception.class );
    }
    /**
     * Method under test: {@link UserServiceImpl#validarId(String)}
     */
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testValidarId2() throws Exception {
        // TODO: Complete this test.
        User user = new User();
        user.setId( "42" );
        Role role = new Role();
        role.setName( "USER" );
        user.setRole( role );
        when( userRepository.findByEmail( anyString() ) ).
                thenReturn( Optional.of( user ) );
        boolean validarId = userServiceImpl.validarId( "42" );
        assertThat( validarId ).isTrue();
    }
    /**
     * Method under test: {@link UserServiceImpl#authenticatedUser()}
     */
    @Test
    @WithMockUser()
    void testAuthenticatedUser() throws Exception {
        // TODO: Complete this test.
when( userRepository.findByEmail( any() ) ).thenReturn( Optional.of( new User() ) );
        UserDto userDto = userServiceImpl.authenticatedUser();
        assertThat( userDto ).isNotNull();
    }

    /**
     * Method under test: {@link UserServiceImpl#authenticatedUser()}
     */
    @Test
    @WithMockUser()
    void testAuthenticatedUser2() throws Exception {
        // TODO: Complete this test.
        when( userRepository.findByEmail( any() ) ).thenReturn( Optional.empty( ) );
        assertThatThrownBy(  () ->userServiceImpl.authenticatedUser()).isInstanceOf( Exception.class );
    }
}

