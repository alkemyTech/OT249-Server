
package com.alkemy.ong.security;

import com.alkemy.ong.service.UserService;
import com.alkemy.ong.utils.JwtUtil;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CustomAuthorizationFilter.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )

class CustomAuthorizationFilterTest {

    @Autowired
    @InjectMocks
    private CustomAuthorizationFilter customAuthorizationFilter;

    private MockHttpServletRequest req;

    private MockHttpServletResponse rsp;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {

        req = new MockHttpServletRequest();
        rsp = new MockHttpServletResponse();

    }

    /**
     * Method under test: {@link CustomAuthorizationFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}
     */
    @Test
    void cuando_se_obtiene_un_authenticacion_deberia_ser_igual_al_devuelto_por_el_service() throws ServletException, IOException {
        //given
        MockFilterChain mockChain = new MockFilterChain();
        Object userDetails = new Object();
        Authentication authenticationToken = new TestingAuthenticationToken( userDetails, null );

        //when
        when( jwtUtil.getAuthentication( any() ) ).thenReturn( authenticationToken );

        //then
        customAuthorizationFilter.doFilter( req, rsp, mockChain );

        assertThat( rsp.getContentAsString() ).isEqualTo( "" );

        assertThat( SecurityContextHolder.getContext().getAuthentication() ).isEqualTo( authenticationToken );
    }


    /**
     * Method under test: {@link CustomAuthorizationFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}
     */
    @Test
    void cuando_tira_una_excepcion_deberia_ser_nulo_el_security_context() throws ServletException, IOException {

        //given
        MockFilterChain mockChain = new MockFilterChain();

        //when
        when( jwtUtil.getAuthentication( any() ) ).thenThrow( new SignatureException( "Error" ) );

        //then
        customAuthorizationFilter.doFilter( req, rsp, mockChain );
        assertThat( rsp.getContentAsString() ).isEqualTo( "" );
        assertThat( SecurityContextHolder.getContext().getAuthentication() ).isNull();
    }
}