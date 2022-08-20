package com.alkemy.ong.controller;

import com.alkemy.ong.dto.RoleDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserResponseDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
import com.alkemy.ong.service.IRoleService;
import com.alkemy.ong.service.UserService;
import com.alkemy.ong.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private IRoleService iRoleService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService, iRoleService,passwordEncoder, authenticationManager, jwtUtil, modelMapper );
    }

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#registrarUsuario(UserDto)}
     */
    @Test
    void testRegistrarUsuario() throws Exception {

        Role role = new Role();
        role.setDescription( "The characteristics of someone or something" );
        role.setId( "42" );
        role.setName( "Name" );
        role.setTimestamp( mock( Timestamp.class ) );
        role.setUsers( new HashSet<>() );
        when( iRoleService.getRoleById( anyString() ) ).thenReturn( role );

        Role role1 = new Role();
        role1.setDescription( "The characteristics of someone or something" );
        role1.setId( "42" );
        role1.setName( "Name" );
        role1.setTimestamp( mock( Timestamp.class ) );
        role1.setUsers( new HashSet<>() );

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
        when( userService.login( any() ) ).thenReturn( new UserResponseDto( "Jwt" ) );
        when( userService.guardarUsuario( any() ) ).thenReturn( user );
        when( passwordEncoder.encode( any() ) ).thenReturn( "secret" );

        UserDto userDto = new UserDto();
        userDto.setDeleted( true );
        userDto.setEmail( "jane.doe@example.org" );
        userDto.setFirstName( "Jane" );
        userDto.setLastName( "Doe" );
        userDto.setPassword( "iloveyou" );
        userDto.setPhoto( "alice.liddell@example.org" );
        userDto.setRole( new RoleDto() );
        String content = (new ObjectMapper()).writeValueAsString( userDto );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post( "/auth/register" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content );


        MockMvcBuilders.standaloneSetup( userController )
                .build()
                .perform( requestBuilder )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( "application/json" ) )
                .andExpect( MockMvcResultMatchers.content().json( "{\"jwt\":\"Jwt\"}" ) );
    }
}

