package com.alkemy.ong.exceptions;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ContextConfiguration(classes = {CustomExceptionController.class})
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )

class CustomExceptionControllerTest {

    /**
     * Method under test: {@link CustomExceptionController#handleAccessDeniedException(AccessDeniedException)}
     */
    @Test
    void testHandleAccessDeniedException() {

        CustomExceptionController customExceptionController = new CustomExceptionController();
        Map<String, String> actualHandleAccessDeniedExceptionResult = customExceptionController
                .handleAccessDeniedException( new AccessDeniedException( "Msg" ) );
        assertEquals( 1, actualHandleAccessDeniedExceptionResult.size() );
        assertEquals( "Msg", actualHandleAccessDeniedExceptionResult.get( "errors" ) );
    }

    /**
     * Method under test: {@link CustomExceptionController#handleAccessDeniedException(AccessDeniedException)}
     */
    @Test
    void testHandleAccessDeniedException2() {
        //given
        CustomExceptionController exceptionController = new CustomExceptionController();
        //when

        //then
        assertThatNullPointerException().isThrownBy( () ->
                exceptionController.handleAccessDeniedException( null )
        );
    }

    /**
     * Method under test: {@link CustomExceptionController#handleAccessDeniedException(AccessDeniedException)}
     */
    @Test
    void testHandleAccessDeniedException3() {

        CustomExceptionController customExceptionController = new CustomExceptionController();
        Map<String, String> actualHandleAccessDeniedExceptionResult = customExceptionController
                .handleAccessDeniedException( new AccessDeniedException( "Msg" ) );
        assertEquals( 1, actualHandleAccessDeniedExceptionResult.size() );
        assertEquals( "Msg", actualHandleAccessDeniedExceptionResult.get( "errors" ) );
    }

    /**
     * Method under test: {@link CustomExceptionController#handleValidateExceptions(BindingResultException)}
     */
    @Test
    void testHandleValidateExceptions() {

        CustomExceptionController customExceptionController = new CustomExceptionController();
        BindingResult bindingResult = mock( BindingResult.class );
        ArrayList<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add( new FieldError( "objectName", "field", "rejectedValue", true, null, null, null ) );
        when( bindingResult.getFieldErrors() ).thenReturn( fieldErrors );
        BindingResultException bindingResultException = new BindingResultException( bindingResult );
        ResponseEntity<?> actualHandleValidateExceptionsResult = customExceptionController
                .handleValidateExceptions( bindingResultException );

        assertTrue( actualHandleValidateExceptionsResult.hasBody() );
        assertTrue( actualHandleValidateExceptionsResult.getHeaders().isEmpty() );
        assertEquals( HttpStatus.BAD_REQUEST, actualHandleValidateExceptionsResult.getStatusCode() );
    }

    /**
     * Method under test: {@link CustomExceptionController#handleValidateExceptions(BindingResultException)}
     */
    @Test
    void testHandleValidateExceptions2() {

        //given
        CustomExceptionController customExceptionController = new CustomExceptionController();
        BindingResult bindingResult = mock( BindingResult.class );
        ArrayList<FieldError> fieldErrors = new ArrayList<>();
        String[] scripts = new String[]{"100"};
        Object[] objects = new Object[]{};
        FieldError fieldError = new FieldError( "field", "field", new Object(), true, scripts, objects, "null" );
        fieldErrors.add( fieldError );
        //when
        when( bindingResult.getFieldErrors() ).thenReturn( fieldErrors );

        ResponseEntity<?> actualHandleValidateExceptionsResult = customExceptionController
                .handleValidateExceptions( new BindingResultException( bindingResult ) );

        //then
        assertThat( actualHandleValidateExceptionsResult.getStatusCode() ).isEqualTo( BAD_REQUEST );
        Object resultBody = actualHandleValidateExceptionsResult.getBody();
        assertThat( resultBody ).isInstanceOf( Map.class );
        assertThat( resultBody ).hasFieldOrProperty( "errorMessage" );
        assertThat( resultBody ).hasFieldOrProperty( "errorMessage" );
        assertThat( resultBody ).hasFieldOrPropertyWithValue( "errorCode", BAD_REQUEST.series() );
        assertThat( resultBody ).hasFieldOrPropertyWithValue( "errorMessage", "Hay errores en lo enviado" );

    }

    /**
     * Method under test: {@link CustomExceptionController#handleRecordNotFound(RecordException.RecordNotFoundException)}
     */
    @Test
    void testHandleRecordNotFound() {
        //given
        CustomExceptionController customExceptionController = new CustomExceptionController();
        //when
        Map<String, String> actualHandleRecordNotFoundResult = customExceptionController
                .handleRecordNotFound( new RecordException.RecordNotFoundException( "An error occurred" ) );
        //then
        assertEquals( 1, actualHandleRecordNotFoundResult.size() );
        assertEquals( "An error occurred", actualHandleRecordNotFoundResult.get( "errors" ) );
    }

    /**
     * Method under test: {@link CustomExceptionController#handleRecordNotFound(RecordException.RecordNotFoundException)}
     */
    @Test
    void testHandleRecordNotFound2() {

        CustomExceptionController customExceptionController = new CustomExceptionController();
        String message = "FAILED";
        Map<String, String> actual = customExceptionController.handleRecordNotFound( new RecordException.RecordNotFoundException( message ) );
        assertThat( actual ).isNotNull();
        assertThat( actual ).hasFieldOrPropertyWithValue( "errors", message );
        assertThat( actual.get( "errors" ) ).isEqualTo( message );
        assertThat( actual.size() ).isEqualTo( 1 );
    }
}

