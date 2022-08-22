
package com.alkemy.ong.security;

import com.alkemy.ong.dto.LoginRequestDTO;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.service.UserService;
import com.alkemy.ong.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith( SpringExtension.class )
@Tag( value = "ut")
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )

class CustomAuthenticationFilterTest {

    @MockBean
    UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    CustomAuthenticationFilter customAuthenticationFilter;

    @BeforeEach
    void setUp() {
        customAuthenticationFilter = new CustomAuthenticationFilter();

    }


    /**
     * Method under test: {@link CustomAuthenticationFilter#attemptAuthentication(HttpServletRequest, HttpServletResponse)}
     */
    @Test
    void testAttemptAuthentication() throws AuthenticationException {


        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        assertNull( customAuthenticationFilter.attemptAuthentication( mockHttpServletRequest, new MockHttpServletResponse() ) );
        assertTrue( mockHttpServletRequest.getInputStream() instanceof DelegatingServletInputStream );
    }

    /**
     * Method under test: {@link CustomAuthenticationFilter#attemptAuthentication(HttpServletRequest, HttpServletResponse)}
     */
    @Test
    void testAttemptAuthentication4() throws AuthenticationException, JsonProcessingException {

        AuthenticationManager authenticationManager = mock( AuthenticationManager.class );
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter( authenticationManager, jwtUtil);
        when( authenticationManager.authenticate( any() ) ).thenReturn( new TestingAuthenticationToken("" ,"" ) );

        MockHttpServletRequest request = new MockHttpServletRequest();
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();


        request.setContent((new ObjectMapper()).writeValueAsBytes(loginRequestDTO ));
        MockHttpServletResponse response = new MockHttpServletResponse();
        Authentication attemptAuthentication = customAuthenticationFilter.attemptAuthentication( request, response );
        assertThat( attemptAuthentication ).isNotNull().isInstanceOf( TestingAuthenticationToken.class );
        verify( authenticationManager ).authenticate( any() );
    }

    /**
     * Method under test: {@link CustomAuthenticationFilter#unsuccessfulAuthentication(HttpServletRequest, HttpServletResponse, AuthenticationException)}
     */
    @Test
    void testUnsuccessfulAuthentication() throws IOException {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        customAuthenticationFilter.unsuccessfulAuthentication( request, response, new AccountExpiredException( "Msg" ) );
        assertThat(  response.getContentAsString()).isNotNull();
    }

    /**
     * Method under test: {@link CustomAuthenticationFilter#unsuccessfulAuthentication(HttpServletRequest, HttpServletResponse, AuthenticationException)}
     */
    @Test
    void testUnsuccessfulAuthentication2() throws IOException {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        customAuthenticationFilter.unsuccessfulAuthentication( request, response, new AccountExpiredException( "Msg" ) );
        assertThat( response.getContentAsString()).isEqualTo("{\"ok\":false}"  );
        assertThat( response.getContentType() ).isEqualTo( MediaType.APPLICATION_JSON_VALUE );
    }

    /**
     * Method under test: {@link CustomAuthenticationFilter#successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication)}
     */
    @Test
    void testSuccessfulAuthentication() throws IOException {

        // Arrange
        // TODO: Populate arranged inputs
        Authentication authentication = mock( Authentication.class );
        AuthenticationManager authenticationManager = mock( AuthenticationManager.class );
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter( authenticationManager, jwtUtil);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        when( authentication.getPrincipal() ).thenReturn( new UserDto() );
        when( jwtUtil.generateToken( any() ) ).thenReturn( "jwtResponse" );
        // Act
        customAuthenticationFilter.successfulAuthentication( request, response, chain, authentication );

        // Assert
        // TODO: Add assertions on result

        assertThat( response.getContentAsString())
                .isNotEmpty().isEqualTo( "{\"jwt\":\"jwtResponse\"}" );
        assertThat( response.getContentType() ).isNull();
        verify( authentication ).getPrincipal();
    }
}