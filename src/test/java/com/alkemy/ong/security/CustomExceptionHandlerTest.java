
package com.alkemy.ong.security;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CustomExceptionHandler.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )

class CustomExceptionHandlerTest {

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    @MockBean(name = "handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    /**
     * Method under test: {@link CustomExceptionHandler#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void test_ok() {

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        customExceptionHandler.doFilterInternal( mockHttpServletRequest, response, filterChain );
        assertFalse( mockHttpServletRequest.isRequestedSessionIdFromURL() );
        assertTrue( mockHttpServletRequest.isRequestedSessionIdFromCookie() );
        assertFalse( mockHttpServletRequest.isAsyncSupported() );
        assertFalse( mockHttpServletRequest.isAsyncStarted() );
        assertTrue( mockHttpServletRequest.isActive() );
        assertTrue( mockHttpServletRequest.getSession() instanceof MockHttpSession );
        assertEquals( "", mockHttpServletRequest.getServletPath() );
        assertEquals( 80, mockHttpServletRequest.getServerPort() );
        assertEquals( "localhost", mockHttpServletRequest.getServerName() );
        assertEquals( "http", mockHttpServletRequest.getScheme() );
        assertEquals( "", mockHttpServletRequest.getRequestURI() );
        assertEquals( 80, mockHttpServletRequest.getRemotePort() );
        assertEquals( "localhost", mockHttpServletRequest.getRemoteHost() );
        assertEquals( "HTTP/1.1", mockHttpServletRequest.getProtocol() );
        assertEquals( "", mockHttpServletRequest.getMethod() );
        assertEquals( 80, mockHttpServletRequest.getLocalPort() );
        assertEquals( "localhost", mockHttpServletRequest.getLocalName() );
        assertTrue( mockHttpServletRequest.getInputStream() instanceof DelegatingServletInputStream );
        assertEquals( DispatcherType.REQUEST, mockHttpServletRequest.getDispatcherType() );
        assertEquals( "", mockHttpServletRequest.getContextPath() );
        assertEquals( -1L, mockHttpServletRequest.getContentLengthLong() );
        assertThat( response.getStatus() ).isEqualTo( OK.code() );
    }


    /**
     * Method under test: {@link CustomExceptionHandler#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal3() throws IOException, ServletException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock( FilterChain.class );
        doThrow( new IOException( "An error occurred" ) ).when( filterChain )
                .doFilter( any(), any() );
        customExceptionHandler.doFilterInternal( request, response, filterChain );
        verify( handlerExceptionResolver ).resolveException( any(), any(),
                any(), any() );
        verify( filterChain ).doFilter( any(), any() );
        assertThat(response.getStatus()).isEqualTo( BAD_REQUEST.code() );
    }

    /**
     * Method under test: {@link CustomExceptionHandler#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal6() throws IOException, ServletException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        Response response = mock( Response.class );
        when( response.getOutputStream() )
                .thenThrow( new IOException(
                        "An error has occurred"));
        doNothing().when( response ).setContentType( any() );
        doNothing().when( response ).setStatus( anyInt() );
        FilterChain filterChain = mock( FilterChain.class );
        doThrow( new IOException( "An error occurred" ) ).when( filterChain )
                .doFilter( any(), any() );
        assertThatThrownBy(
                () -> customExceptionHandler.doFilterInternal( request, response, filterChain ) ).isInstanceOf(  RuntimeException.class)
                .hasMessage("java.io.IOException: An error has occurred" );
        verify( handlerExceptionResolver ).resolveException( any(), any(),
                any(), any() );
        verify( response ).getOutputStream();
        verify( response ).setContentType( any() );
        verify( response ).setStatus( anyInt() );
        verify( filterChain ).doFilter( any(), any() );
    }
}