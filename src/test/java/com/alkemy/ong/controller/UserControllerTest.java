package com.alkemy.ong.controller;

import com.alkemy.ong.dto.LoginRequestDTO;
import com.alkemy.ong.dto.RoleDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserResponseDto;
import com.alkemy.ong.exceptions.CustomExceptionController;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
import com.alkemy.ong.service.IRoleService;
import com.alkemy.ong.service.UserService;
import com.alkemy.ong.utils.PageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )

class UserControllerTest {
    @MockBean
    private UserService userService;

    @MockBean
    private IRoleService iRoleService;

    @SpyBean
    private ModelMapper modelMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private UserController userController;

    /**
     * Method under test: {@link UserController#deleteUser(String)}
     */
    @Test
    void testDeleteUser() throws Exception {

        when( userService.deleteUser( anyString() ) ).thenReturn( true );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/users/{id}", "42" );
        MockMvcBuilders.standaloneSetup( userController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() );
    }

    /**
     * Method under test: {@link UserController#deleteUser(String)}
     */
    @Test
    void testDeleteUser2() throws Exception {

        when( userService.deleteUser( anyString() ) ).thenReturn( false );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete( "/users/{id}", "42" );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( userController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().isNotFound() );
    }

    @BeforeEach
    void setUp() {

        userController = new UserController( userService, iRoleService, passwordEncoder, modelMapper );
    }

    /**
     * Method under test: {@link UserController#AuthenticatedUser()}
     */
    @Test
    void testAuthenticatedUser() throws Exception {

        when( userService.authenticatedUser() ).thenReturn( new UserDto() );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/auth/me" );
        MockMvcBuilders.standaloneSetup( userController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "application/json" ) )
                .andExpect( MockMvcResultMatchers.content()
                        .string(
                                "{\"firstName\":null,\"lastName\":null,\"email\":null,\"password\":null,\"photo\":null,\"role\":null,\"deleted"
                                        + "\":false}" ) );
    }

    /**
     * Method under test: {@link UserController#getPagedUsers(int, String)}
     */
    @Test
    void testGetPagedUsers() throws Exception {

        when( userService.getAllUsers( anyInt(), any() ) ).thenReturn( new PageImpl<>( new ArrayList<>(), PageUtils.getPageable( 0, "asc" ),0 ) );
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get( "/users" ).param( "order", "foo" );
        MockHttpServletRequestBuilder requestBuilder = paramResult.param( "page", String.valueOf( 1 ) );
        MockMvcBuilders.standaloneSetup( userController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "application/json" ) )
                .andExpect( MockMvcResultMatchers.content()
                        .json(
                                "{\"content\":[],\"pageable\":{\"sort\":{\"sorted\":true,\"unsorted\":false,\"empty\":false},\"pageNumber\":0,\"pageSize\":10,\"offset\":0,\"paged\":true,\"unpaged\":false},\"totalElements\":0,\"totalPages\":0,\"last\":true,\"numberOfElements\":0,\"size\":10,\"number\":0,\"first\":true,\"sort\":{\"sorted\":true,\"unsorted\":false,\"empty\":false},\"empty\":true}\n" ) );
    }


    /**
     * Method under test: {@link UserController#registrarUsuario(UserDto)}
     */
    @Test
    void testRegistrarUsuario() throws Exception {

        Role role = getRole();
        when( iRoleService.getRoleById( anyString() ) ).thenReturn( role );

        Role role1 = getRole();

        User user = getUser( role1 );
        when( userService.login( any() ) ).thenReturn( new UserResponseDto( "Jwt" ) );
        when( userService.guardarUsuario( any() ) ).thenReturn( user );
        when( passwordEncoder.encode( any() ) ).thenReturn( "secret" );

        UserDto userDto = getUserDto();
        String content = (new ObjectMapper()).writeValueAsString( userDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/auth/register" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );


        MockMvcBuilders.standaloneSetup( userController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isCreated() )
                .andExpect( MockMvcResultMatchers.content().contentType( "application/json" ) )
                .andExpect( MockMvcResultMatchers.content().json( "{\"jwt\":\"Jwt\"}" ) );
    }

    @Test
    void testRegistrarUsuario_cuando_se_pasa_un_campo_vacio() throws Exception {

        Role role = getRole();
        when( iRoleService.getRoleById( anyString() ) ).thenReturn( role );

        Role role1 = getRole();

        User user = getUser( role1 );
        when( userService.login( any() ) ).thenReturn( new UserResponseDto( "Jwt" ) );
        when( userService.guardarUsuario( any() ) ).thenReturn( user );
        when( passwordEncoder.encode( any() ) ).thenReturn( "secret" );

        UserDto userDto = getUserDto();
        userDto.setFirstName("");
        String content = (new ObjectMapper()).writeValueAsString( userDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/auth/register" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );


        MockMvcBuilders.standaloneSetup( userController ).setControllerAdvice(new CustomExceptionController())
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.content().contentType( "application/json" ) )
                .andExpect( MockMvcResultMatchers.content().json( "{\"errorMessage\":\"Hay errores en lo enviado\",\"errorCode\":\"CLIENT_ERROR\",\"errorFields\":[{\"field\":\"firstName\",\"message\":\"must not be blank\",\"code\":\"NotBlank\"}]}\n" ) );
    }

    private static UserDto getUserDto() {

        UserDto userDto = new UserDto();
        userDto.setDeleted( true );
        userDto.setEmail( "jane.doe@example.org" );
        userDto.setFirstName( "Jane" );
        userDto.setLastName( "Doe" );
        userDto.setPassword( "iloveyou" );
        userDto.setPhoto( "alice.liddell@example.org" );
        userDto.setRole( new RoleDto() );
        return userDto;
    }

    private static User getUser(Role role1) {

        User user = new User();
        user.setDeleted( true );
        user.setEmail( "jane.doe@example.org" );
        user.setFirstName( "Jane" );
        user.setId( "42" );
        user.setLastName( "Doe" );
        user.setPassword( "iloveyou" );
        user.setPhoto( "alice.liddell@example.org" );
        user.setRole( role1 );
        user.setTimestamp( mock( Timestamp.class ) );
        return user;
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

    /**
     * Method under test: {@link UserController#registrarUsuario(UserDto)}
     */
    @Test
    void testRegistrarUsuario2() throws Exception {

        Role role = getRole();
        when( iRoleService.getRoleById( anyString() ) ).thenReturn( role );

        Role role1 = getRole();

        User user = getUser( role1 );
        when( userService.login(  any() ) ).thenReturn( new UserResponseDto( "Jwt" ) );
        when( userService.guardarUsuario(  any() ) ).thenReturn( user );
        when( passwordEncoder.encode( any() ) ).thenReturn( "secret" );

        UserDto userDto = getUserDto();
        String content = (new ObjectMapper()).writeValueAsString( userDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/auth/register" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );
        MockMvcBuilders.standaloneSetup( userController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isCreated() )
                .andExpect( MockMvcResultMatchers.content().contentType( "application/json" ) )
                .andExpect( MockMvcResultMatchers.content().string( "{\"jwt\":\"Jwt\"}" ) );
    }

    /**
     * Method under test: {@link UserController#updateUser(String, Map)}
     */
    @Test
    void testUpdateUser() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch( "/users/{id}", "42" );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( userController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().is( 400 ) );
    }

    /**
     * Method under test: {@link UserController#updateUser(String, Map)}
     */
    @Test
    void testUpdateUser2() throws Exception {

        UserDto userDto = new UserDto();
        User value = new User();
        when( userService.findById( anyString() ) ).thenReturn( Optional.of( value ) );
        when( userService.guardarUsuario( any() ) ).thenReturn(  value );
        String writeValueAsString = (new ObjectMapper()).writeValueAsString( userDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch( "/users/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON ).content( writeValueAsString );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( userController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.
                andExpect( MockMvcResultMatchers.status().is( 200 ) )
                .andExpect( MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( MockMvcResultMatchers.content().json(writeValueAsString) )
        ;
    }

    /**
     * Method under test: {@link UserController#updateUser(String, Map)}
     */
    @Test
    void testUpdateUser3() throws Exception {

        UserDto userDto = new UserDto();
        when( userService.findById( anyString() ) ).thenThrow( new NullPointerException(
                "ERROR"
        ) );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch( "/users/{id}", "42" )
                .contentType( MediaType.APPLICATION_JSON ).content( (new ObjectMapper()).writeValueAsString( userDto ) );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( userController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().is( HttpStatus.NOT_FOUND.value() ) );
    }

    @Test
    void login() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/auth/login")
                .contentType( MediaType.APPLICATION_JSON ).content( (new ObjectMapper()).writeValueAsString( new LoginRequestDTO()) );
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup( userController )
                .build()
                .perform( requestBuilder );
        actualPerformResult.andExpect( MockMvcResultMatchers.status().is( HttpStatus.OK.value() ) );

    }
}

